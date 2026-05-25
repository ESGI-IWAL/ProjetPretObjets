package com.aliw.pretemoica.tests;

import static org.junit.jupiter.api.Assertions.*;

import com.aliw.pretemoica.entity.LendingEntity;
import com.aliw.pretemoica.entity.ObjectEntity;
import com.aliw.pretemoica.entity.UserEntity;
import com.aliw.pretemoica.service.LendingService;
import com.aliw.pretemoica.service.ObjectService;
import com.aliw.pretemoica.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests d'intégration pour vérifier le flux complet via les services.
 * Simule les interactions de l'API en appelant directement les services.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RestApiIntegrationTest {

  @Autowired private UserService userService;
  @Autowired private ObjectService objectService;
  @Autowired private LendingService lendingService;

  private UserEntity borrower;
  private UserEntity lender;
  private ObjectEntity objectToLend;

  @BeforeEach
  void setUp() {
    UserEntity borrowerEntity = new UserEntity();
    borrowerEntity.setUsername("borrower_api_" + System.nanoTime());
    borrowerEntity.setEmail("borrower_" + System.nanoTime() + "@example.com");
    borrowerEntity.setPassword("pass");
    borrowerEntity.setRating(4);
    borrower = userService.create(borrowerEntity);

    UserEntity lenderEntity = new UserEntity();
    lenderEntity.setUsername("lender_api_" + System.nanoTime());
    lenderEntity.setEmail("lender_" + System.nanoTime() + "@example.com");
    lenderEntity.setPassword("pass");
    lenderEntity.setRating(5);
    lender = userService.create(lenderEntity);

    ObjectEntity obj = new ObjectEntity();
    obj.setName("Drill_" + System.nanoTime());
    obj.setDescription("Electric drill");
    obj.setOwnedBy(lender);
    obj.setWeight(2.5);
    obj.setDimensions("20x10x10");
    obj.setStateOfWear(ObjectEntity.ObjectStateOfWear.GOOD);
    obj.setCategory(ObjectEntity.ObjectCategories.TOOLS);
    obj.setMaterial(ObjectEntity.ObjectMaterial.PLASTIC);
    objectToLend = objectService.create(obj);
  }

  /**
   * Test : créer un prêt via le service
   */
  @Test
  void testCreateLendingViaService() {
    LendingEntity lending = new LendingEntity();
    lending.setBorrowedBy(borrower);
    lending.setOfferedBy(lender);
    lending.setObject(objectToLend);
    lending.setStartedAt(LocalDateTime.now());

    LendingEntity created = lendingService.create(lending);

    assertNotNull(created.getId());
    assertEquals(borrower.getId(), created.getBorrowedBy().getId());
    assertEquals(lender.getId(), created.getOfferedBy().getId());
  }

  /**
   * Test : récupérer tous les prêts
   */
  @Test
  void testGetAllLendingsViaService() {
    LendingEntity lending = new LendingEntity();
    lending.setBorrowedBy(borrower);
    lending.setOfferedBy(lender);
    lending.setObject(objectToLend);
    lendingService.create(lending);

    List<LendingEntity> allLendings = lendingService.getAll();

    assertEquals(1, allLendings.size());
  }

  /**
   * Test : obtenir les utilisateurs via le service
   */
  @Test
  void testGetUsersViaService() {
    List<UserEntity> users = userService.getAll();

    assertTrue(users.size() >= 2);
  }

  /**
   * Test : créer un utilisateur via le service
   */
  @Test
  void testCreateUserViaService() {
    UserEntity newUser = new UserEntity();
    newUser.setUsername("newuser_" + System.nanoTime());
    newUser.setEmail("newuser_" + System.nanoTime() + "@example.com");
    newUser.setPassword("password");
    newUser.setRating(3);

    UserEntity created = userService.create(newUser);

    assertNotNull(created.getId());
    assertTrue(created.getUsername().startsWith("newuser_"));
  }

  /**
   * Test : intégration complète User -> Object -> Lending
   */
  @Test
  void testCompleteWorkflowIntegration() {
    // Créer deux utilisateurs
    UserEntity alice = new UserEntity();
    alice.setUsername("alice_complete_" + System.nanoTime());
    alice.setEmail("alice_complete_" + System.nanoTime() + "@example.com");
    alice.setPassword("pass");
    alice.setRating(5);
    UserEntity createdAlice = userService.create(alice);

    UserEntity bob = new UserEntity();
    bob.setUsername("bob_complete_" + System.nanoTime());
    bob.setEmail("bob_complete_" + System.nanoTime() + "@example.com");
    bob.setPassword("pass");
    bob.setRating(4);
    UserEntity createdBob = userService.create(bob);

    // Créer un objet
    ObjectEntity object = new ObjectEntity();
    object.setName("Ladder_" + System.nanoTime());
    object.setDescription("Aluminum ladder");
    object.setOwnedBy(createdAlice);
    object.setWeight(5.0);
    object.setDimensions("2x1x0.5");
    object.setStateOfWear(ObjectEntity.ObjectStateOfWear.GOOD);
    object.setCategory(ObjectEntity.ObjectCategories.TOOLS);
    object.setMaterial(ObjectEntity.ObjectMaterial.METAL);
    ObjectEntity createdObject = objectService.create(object);

    // Créer un prêt
    LendingEntity lending = new LendingEntity();
    lending.setBorrowedBy(createdBob);
    lending.setOfferedBy(createdAlice);
    lending.setObject(createdObject);
    LendingEntity createdLending = lendingService.create(lending);

    // Vérifier que tous les éléments sont correctement liés
    assertNotNull(createdLending.getId());
    assertEquals(createdAlice.getId(), createdLending.getOfferedBy().getId());
    assertEquals(createdBob.getId(), createdLending.getBorrowedBy().getId());
    assertEquals(createdObject.getId(), createdLending.getObject().getId());
    assertEquals(createdAlice.getId(), createdObject.getOwnedBy().getId());
  }
}





