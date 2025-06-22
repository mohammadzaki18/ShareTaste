// File: Interfaces/RecipeDisplayable.java
package Interfaces;

/**
 * Interface untuk menampilkan resep makanan/minuman.
 * Setiap kelas yang mengimplementasikan interface ini
 * harus dapat memuat dan menampilkan daftar resep.
 */
public interface RecipeDisplayable {
    void loadData();
    void loadMyRecipes();
    void loadOtherRecipes();
}
