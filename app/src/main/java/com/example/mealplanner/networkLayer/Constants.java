package com.example.mealplanner.networkLayer;

public class Constants {
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public static final String BASE_IMG_URL = "https://www.themealdb.com/images/ingredients/";

    public class APIEndpoints {
        public static final String RANDOM_MEAL = "random.php";
        public static final String LOOKUP_MEAL = "lookup.php";
        public static final String SEARCH_MEAL = "search.php";
        public static final String MEAL_CATEGORIES = "categories.php";
        public static final String FILTER_MEALS = "filter.php";
        public static final String LIST_ALL = "list.php";

    }

    public class Keys {
        public static final String RANDOM_MEAL = "randomMeal";
        public static final String CATEGORY_NAMES = "categoryNames";
        public static final String MEALS_LIST = "mealsList";
        public static final String CATEGORY = "Category";
        public static final String INGREDIENT = "Ingredient";
        public static final String AREA = "Area";



    }

}
