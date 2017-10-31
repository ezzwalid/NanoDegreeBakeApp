package com.ezz.bakeappudacity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ezz.bakeappudacity.helpers.Utils;
import com.ezz.bakeappudacity.recipe.model.Recipe;
import com.ezz.bakeappudacity.recipe.ui.RecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;


/**
 * Created by EzzWalid on 10/30/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    @Rule
    public ActivityTestRule<RecipeActivity> activityTestRule = new ActivityTestRule<>(RecipeActivity.class);
    private IdlingResource idlingResource;

    @Before
    public void setUp() throws Exception{
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void RecipeFragmentTest() throws InterruptedException{
        ArrayList<Recipe> recipes = activityTestRule.getActivity().recipes;
        Thread.sleep(2000);
        onView(withText(recipes.get(0).getName())).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_recycler_view)).perform(actionOnItemAtPosition(0, click()));
        Thread.sleep(2000);
        onView(withId(R.id.steps_recycler_view)).perform(actionOnItemAtPosition(2, click()));
        onView(allOf(withId(R.id.step_instructions), isDisplayed())).check(matches(withText(recipes.get(0).getSteps().get(2).getDescription())));
        Thread.sleep(2000);
        onView(withId(R.id.steps_view_pager)).perform(swipeLeft());
        Thread.sleep(2000);
        onView(allOf(withId(R.id.step_instructions), isDisplayed())).check(matches(withText(recipes.get(0).getSteps().get(3).getDescription())));
        Thread.sleep(2000);
        onView(withId(R.id.steps_view_pager)).perform(swipeRight());
        Thread.sleep(2000);
        onView(allOf(withId(R.id.step_instructions), isDisplayed())).check(matches(withText(recipes.get(0).getSteps().get(2).getDescription())));
        Thread.sleep(2000);
        if (!Utils.isTablet(activityTestRule.getActivity())){
            pressBack();
        }
        onView(withId(R.id.ingredient_btn)).perform(click());
        Thread.sleep(2000);
        onView(withText(recipes.get(0).getIngredients().get(0).getIngredient())).check(matches(isDisplayed()));

    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }

}
