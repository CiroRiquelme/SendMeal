package com.example.sendmeal;

import android.os.SystemClock;

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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;


@RunWith(JUnit4.class)
@LargeTest
public class BusquedaDePlatosActivityTest {

    @Rule
    public ActivityTestRule<BusquedaDePlatosActivity> activityRule =
            new ActivityTestRule<>(BusquedaDePlatosActivity.class);

/*    TextInputEditText etNombre;
    TextInputEditText etPrecioMin;
    TextInputEditText etPrecioMax;

    MaterialButton btnBuscar;
    MaterialButton btnCancelar;*/

    @Test
    public void BusquedaPlatoDatosNombreValido() {
        onView(withId(R.id.etPlatoNombreBuscar)).perform(typeText("nombre"));
/*        onView(withId(R.id.etPlatoPrecioMin)).perform(typeText("1"));
        onView(withId(R.id.etPlatoPrecioMax)).perform(typeText("100"));*/

        onView(withId(R.id.btnBuscar)).perform(scrollTo(),click());

        SystemClock.sleep(3000);

/*        onView(withId(R.id.etPlatoNombreBuscar)).check(matches(withText("")));
        onView(withId(R.id.etPlatoPrecioMin)).check(matches(withText("")));
        onView(withId(R.id.etPlatoPrecioMax)).check(matches(withText("")));*/

    }
    @Test
    public void BusquedaPlatoDatosPrecioMinMaxValidos() {
        //onView(withId(R.id.etPlatoNombreBuscar)).perform(typeText("nombre"));
        onView(withId(R.id.etPlatoPrecioMin)).perform(typeText("1"));
        onView(withId(R.id.etPlatoPrecioMax)).perform(typeText("100"));

        onView(withId(R.id.btnBuscar)).perform(scrollTo(),click());

        SystemClock.sleep(3000);

/*        onView(withId(R.id.etPlatoNombreBuscar)).check(matches(withText("")));
        onView(withId(R.id.etPlatoPrecioMin)).check(matches(withText("")));
        onView(withId(R.id.etPlatoPrecioMax)).check(matches(withText("")));*/

    }

}