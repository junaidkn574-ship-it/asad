package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.models.HospitalData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Rehman Hospital", appName)
  }

  @Test
  fun `verify hospital departments loaded`() {
    val depts = HospitalData.departments
    assertTrue(depts.isNotEmpty())
    assertNotNull(depts.find { it.id == "cardiology" })
    assertNotNull(depts.find { it.id == "gynecology" })
    assertNotNull(depts.find { it.id == "pediatrics" })
    assertNotNull(depts.find { it.id == "surgery" })
    assertNotNull(depts.find { it.id == "emergency" })
  }

  @Test
  fun `verify emergency contact configuration`() {
    assertEquals("0946-728100", HospitalData.EMERGENCY_PHONE)
    assertEquals("Saidu Sharif, Swat", HospitalData.LOCATION_CITY)
  }
}
