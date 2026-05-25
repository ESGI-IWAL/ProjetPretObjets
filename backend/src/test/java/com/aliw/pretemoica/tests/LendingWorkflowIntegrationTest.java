package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.entity.*;
import com.aliw.pretemoica.repository.*;
import com.aliw.pretemoica.service.*;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LendingWorkflowIntegrationTest {

  @Autowired private UserService userService;
  @Autowired private ObjectService objectService;
  @Autowired private LendingService lendingService;
  @Autowired private LendingHistoryService lendingHistoryService;

  @Autowired private ObjectRepository objectRepository;
  @Autowired private LendingRepository lendingRepository;
  @Autowired private LendingHistoryRepository lendingHistoryRepository;

  private UserEntity borrower;
  private UserEntity lender;
  private ObjectEntity objectToLend;

  @BeforeEach
  void setUp() {
    // Créer deux utilisateurs : emprunteur et prêteur
    UserEntity borrowerEntity = new UserEntity();
    borrowerEntity.setUsername("borrower_lending_" + System.nanoTime());
    borrowerEntity.setEmail("bob_" + System.nanoTime() + "@example.com");
    borrowerEntity.setPassword("password123");
    borrowerEntity.setRating(4);
    borrower = userService.create(borrowerEntity);

    UserEntity lenderEntity = new UserEntity();
    lenderEntity.setUsername("lender_lending_" + System.nanoTime());
    lenderEntity.setEmail("alice_" + System.nanoTime() + "@example.com");
    lenderEntity.setPassword("password456");
    lenderEntity.setRating(5);
    lender = userService.create(lenderEntity);

    // Créer un objet appartenant au prêteur
    ObjectEntity obj = new ObjectEntity();
    obj.setName("Perceuse électrique");
    obj.setDescription("Perceuse en bon état");
    obj.setOwnedBy(lender);
    obj.setWeight(2.5);
    obj.setDimensions("20x10x10");
    obj.setStateOfWear(ObjectEntity.ObjectStateOfWear.GOOD);
    obj.setCategory(ObjectEntity.ObjectCategories.TOOLS);
    obj.setMaterial(ObjectEntity.ObjectMaterial.PLASTIC);
    objectToLend = objectService.create(obj);
  }

  /**
   * Test du flux complet de prêt :
   * 1. Créer un prêt entre deux utilisateurs
   * 2. Vérifier que le prêt est en base de données
   * 3. Archiver le prêt dans l'historique
   * 4. Vérifier que l'historique est enregistré
   */
  @Test
  void testCompleteLendingWorkflow() {
    // 1. Créer un prêt
    LendingEntity lending = new LendingEntity();
    lending.setBorrowedBy(borrower);
    lending.setOfferedBy(lender);
    lending.setObject(objectToLend);
    lending.setStartedAt(LocalDateTime.now());
    lending.setStatus(LendingStatus.IN_PROGRESS);

    LendingEntity savedLending = lendingService.create(lending);

    // 2. Vérifier que le prêt est sauvegardé et récupérable
    assertNotNull(savedLending.getId());
    LendingEntity retrievedLending = lendingService.getById(savedLending.getId());
    assertEquals(borrower.getId(), retrievedLending.getBorrowedBy().getId());
    assertEquals(lender.getId(), retrievedLending.getOfferedBy().getId());
    assertEquals(objectToLend.getId(), retrievedLending.getObject().getId());
    assertEquals(LendingStatus.IN_PROGRESS, retrievedLending.getStatus());

    // 3. Terminer le prêt et l'archiver dans l'historique
    LocalDateTime endedAt = LocalDateTime.now().plusDays(7);
    retrievedLending.setEndedAt(endedAt);
    retrievedLending.setStatus(LendingStatus.COMPLETED);

    LendingHistoryEntity history = new LendingHistoryEntity();
    history.setBorrowedBy(retrievedLending.getBorrowedBy());
    history.setOfferedBy(retrievedLending.getOfferedBy());
    history.setObject(retrievedLending.getObject());
    history.setStartedAt(retrievedLending.getStartedAt());
    history.setEndedAt(endedAt);

    LendingHistoryEntity savedHistory = lendingHistoryService.create(history);

    // 4. Vérifier que l'historique est enregistré
    assertNotNull(savedHistory.getId());
    LendingHistoryEntity retrievedHistory = lendingHistoryService.getById(savedHistory.getId());
    assertEquals(borrower.getId(), retrievedHistory.getBorrowedBy().getId());
    assertEquals(lender.getId(), retrievedHistory.getOfferedBy().getId());
    assertEquals(objectToLend.getId(), retrievedHistory.getObject().getId());
    assertEquals(endedAt, retrievedHistory.getEndedAt());

    // Vérifier que le prêt existe toujours
    assertEquals(1, lendingRepository.count());
    assertEquals(1, lendingHistoryRepository.count());
  }

  /**
   * Test : vérifier qu'un utilisateur peut avoir plusieurs prêts
   */
  @Test
  void testUserCanHaveMultipleLendings() {
    // Créer 3 objets
    List<ObjectEntity> objects = new java.util.ArrayList<>();
    for (int i = 1; i <= 3; i++) {
      ObjectEntity obj = new ObjectEntity();
      obj.setName("Objet " + i);
      obj.setDescription("Description " + i);
      obj.setOwnedBy(lender);
      obj.setWeight(1.0);
      obj.setDimensions("10x10x10");
      obj.setStateOfWear(ObjectEntity.ObjectStateOfWear.GOOD);
      obj.setCategory(ObjectEntity.ObjectCategories.TOOLS);
      obj.setMaterial(ObjectEntity.ObjectMaterial.PLASTIC);
      objects.add(objectService.create(obj));
    }

    // Créer 3 prêts pour le même emprunteur
    for (ObjectEntity obj : objects) {
      LendingEntity lending = new LendingEntity();
      lending.setBorrowedBy(borrower);
      lending.setOfferedBy(lender);
      lending.setObject(obj);
      lendingService.create(lending);
    }

    // Vérifier que l'emprunteur a bien 3 prêts
    List<LendingEntity> borrowerLendings =
        lendingRepository.findAll().stream()
            .filter(l -> l.getBorrowedBy().getId().equals(borrower.getId()))
            .toList();
    assertEquals(3, borrowerLendings.size());
  }

  /**
   * Test : vérifier la relation entre Lending et LendingHistory
   */
  @Test
  void testLendingHistoryTracksLendingProgression() {
    // Créer un prêt initial
    LendingEntity lending = new LendingEntity();
    lending.setBorrowedBy(borrower);
    lending.setOfferedBy(lender);
    lending.setObject(objectToLend);
    lending.setStartedAt(LocalDateTime.now());
    LendingEntity savedLending = lendingService.create(lending);

    // Simuler une archivage dans l'historique
    LendingHistoryEntity history = new LendingHistoryEntity();
    history.setBorrowedBy(savedLending.getBorrowedBy());
    history.setOfferedBy(savedLending.getOfferedBy());
    history.setObject(savedLending.getObject());
    history.setStartedAt(savedLending.getStartedAt());
    history.setEndedAt(LocalDateTime.now().plusDays(7));

    LendingHistoryEntity savedHistory = lendingHistoryService.create(history);

    // Vérifier l'intégrité référentielle
    assertEquals(
        savedLending.getBorrowedBy().getId(), savedHistory.getBorrowedBy().getId());
    assertEquals(
        savedLending.getOfferedBy().getId(), savedHistory.getOfferedBy().getId());
    assertEquals(
        savedLending.getObject().getId(), savedHistory.getObject().getId());
  }

  /**
   * Test : vérifier que les objets sont bien liés à leurs propriétaires
   */
  @Test
  void testObjectOwnershipIntegrity() {
    // Vérifier que l'objet créé appartient au prêteur
    ObjectEntity retrieved = objectRepository.findById(objectToLend.getId()).orElse(null);
    assertNotNull(retrieved);
    assertEquals(lender.getId(), retrieved.getOwnedBy().getId());

    // Vérifier que le prêteur possède cet objet
    List<ObjectEntity> lenderObjects =
        objectRepository.findAll().stream()
            .filter(o -> o.getOwnedBy().getId().equals(lender.getId()))
            .toList();
    assertTrue(lenderObjects.stream().anyMatch(o -> o.getId().equals(objectToLend.getId())));
  }

  /**
   * Test : vérifier la suppression en cascade
   */
  @Test
  void testLendingDeletionDoesNotAffectRelatedData() {
    // Créer un prêt
    LendingEntity lending = new LendingEntity();
    lending.setBorrowedBy(borrower);
    lending.setOfferedBy(lender);
    lending.setObject(objectToLend);
    LendingEntity savedLending = lendingService.create(lending);

    // Supprimer le prêt
    lendingService.delete(savedLending.getId());

    // Vérifier que le prêt est supprimé
    assertThrows(
        Exception.class,
        () -> lendingService.getById(savedLending.getId()),
        "Lending should be deleted");

    // Vérifier que les utilisateurs et objets existent toujours
    assertNotNull(userService.getById(borrower.getId()));
    assertNotNull(userService.getById(lender.getId()));
    assertNotNull(objectService.getById(objectToLend.getId()));
  }

  /**
   * Test de validation des dates du LendingHistory
   */
  @Test
  void testLendingHistoryDateValidation() {
    LendingHistoryEntity history = new LendingHistoryEntity();
    LocalDateTime start = LocalDateTime.now();
    LocalDateTime end = start.plusDays(7);

    history.setStartedAt(start);
    history.setEndedAt(end);

    // Vérifier qu'il n'y a pas d'erreur de date
    assertFalse(history.hasDateError(), "Valid dates should not produce error");

    // Tester avec des dates invalides
    LendingHistoryEntity invalidHistory = new LendingHistoryEntity();
    invalidHistory.setStartedAt(end);
    invalidHistory.setEndedAt(start); // End before start
    assertTrue(invalidHistory.hasDateError(), "Invalid dates should produce error");
  }
}





