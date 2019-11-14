package com.example.sendmeal;

import android.app.Activity;

import androidx.test.espresso.action.TypeTextAction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(JUnit4.class)
@LargeTest
public class TestAltaPlatoEspresso {


/*    @Rule
    public ActivityTestRule ABMProyectoActivity > mActivityRule
new ActivityTestRule ABMProyectoActivity. class
    @Test
    public void existeTextViewNombreProyecto ()
    onView withText (("Nombre del
                             .
                     check matches isDisplayed*/

    @Rule
    public ActivityTestRule<AltaPlatosActivity> activityRule =
            new ActivityTestRule<>(AltaPlatosActivity.class);

    @Test
    public void AltaPlatoDatosValidos() {
        onView(withId(R.id.etPlatoId)).perform(typeText("10"));
        onView(withId(R.id.etPlatoNombre)).perform(typeText("hamborguesa"));
        onView(withId(R.id.etPlatoDescripcion)).perform(typeText("con mucho queso"));
        onView(withId(R.id.etPlatoPrecio)).perform(typeText("50"));
        onView(withId(R.id.etPlatoCalorias)).perform(typeText("0"));

        onView(withId(R.id.btnPlatoGuardar)).perform(scrollTo(),click());

        onView(withId(R.id.etPlatoId)).check(matches(withText("")));
        onView(withId(R.id.etPlatoNombre)).check(matches(withText("")));
        onView(withId(R.id.etPlatoDescripcion)).check(matches(withText("")));
        onView(withId(R.id.etPlatoPrecio)).check(matches(withText("")));
        onView(withId(R.id.etPlatoCalorias)).check(matches(withText("")));
    }

    @Test
    public void AltaPlatoDatosValidosInvalidos() {
        onView(withId(R.id.etPlatoId)).perform(typeText(""));
        onView(withId(R.id.etPlatoNombre)).perform(typeText("hamborguesa"));
        onView(withId(R.id.etPlatoDescripcion)).perform(typeText("con mucho queso"));
        onView(withId(R.id.etPlatoPrecio)).perform(typeText("50"));
        onView(withId(R.id.etPlatoCalorias)).perform(typeText("0"));

        onView(withId(R.id.btnPlatoGuardar)).perform(scrollTo(),click());

        onView(withId(R.id.etPlatoId)).perform(click());
        onView(withId(R.id.etPlatoId)).check(matches(hasErrorText("id obligatorio")));

    }




}
