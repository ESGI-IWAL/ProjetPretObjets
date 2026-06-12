import { test, expect } from '@playwright/test';

test('test', async ({ page }) => {
  // Click text=S'inscrire
  await page.locator('text=Pas encore de compte ? Inscrivez-vous').click();
  await expect(page).toHaveURL('http://localhost:3000/register');
  // Fill input[name="username"]
  await page.locator('input[id="username"]').fill('test');
  // Fill input[name="email"]
  await page.locator('input[id="email"]').fill('test@test.com');
  // Fill input[name="password"]
  await page.locator('input[id="password"]').fill('password');
  // Fill input[name="password"]
  await page.locator('input[id="passwordConfirm"]').fill('password');
  // Click button with class register-btn
  await page.locator('.register-btn').click();
  await expect(page).toHaveURL('http://localhost:3000/');
});
