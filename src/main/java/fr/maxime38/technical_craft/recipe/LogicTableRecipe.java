package fr.maxime38.technical_craft.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.maxime38.technical_craft.TechnicalCraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LogicTableRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public LogicTableRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer container, Level level) {

        if(level.isClientSide()) return false;

        return recipeItems.get(0).test(container.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<LogicTableRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "logic_table"; //very important
    }

    public static class Serializer implements RecipeSerializer<LogicTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation IS = new ResourceLocation(TechnicalCraft.MODID, "logic_table");

        @Override
        public LogicTableRecipe fromJson(ResourceLocation resourceLocation, JsonObject recipeID) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(recipeID, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(recipeID, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }
            return new LogicTableRecipe(resourceLocation, output, inputs);
        }

        @Override
        public @Nullable LogicTableRecipe fromNetwork(ResourceLocation recipeID, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            return new LogicTableRecipe(recipeID, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, LogicTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for(Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
