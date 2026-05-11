
export async function createLoan(data) {
  const response = await client.post("/loans", data)

  return response.data
}

// GET all
// GET by ID
// POST RECHERCHE { via objet, via utilisateur, via date debut, via date fin}
// POST CREATION { objet, utilisateur, date debut, date fin}
// PUT by ID { date fin}
// DELETE by Id  