package com.example.data.models

object HospitalData {
    const val HOSPITAL_NAME = "Rehman Medical Hospital"
    const val HOSPITAL_TAGLINE = "Excellence in Patient-Centered Healthcare"
    const val LOCATION_CITY = "Saidu Sharif, Swat"
    const val FULL_ADDRESS = "Saidu Sharif Road, near Serena & Saidu Teaching Hospital Sector, Saidu Sharif, Swat, Khyber Pakhtunkhwa, Pakistan"
    const val EMERGENCY_PHONE = "0946-728100"
    const val EMERGENCY_PHONE_ALT = "0946-728101"
    const val AMBULANCE_PHONE = "0946-728102"
    const val RESCUE_1122 = "1122"
    const val OPD_APPOINTMENT_PHONE = "0946-728103"
    const val BLOOD_BANK_PHONE = "0946-728105"
    const val HOSPITAL_EMAIL = "info@rehmanhospital-swat.com"
    const val MAPS_QUERY_URL = "geo:34.7505,72.3570?q=Rehman+Medical+Hospital+Saidu+Sharif+Swat"

    val departments = listOf(
        Department(
            id = "cardiology",
            name = "Cardiology & Cath Lab",
            shortDescription = "Comprehensive cardiac diagnosis, angiography, echocardiography, and 24/7 emergency heart care.",
            fullDescription = "The Department of Cardiology at Rehman Medical Hospital Saidu Sharif is equipped with advanced non-invasive and invasive diagnostic technologies. We offer 24/7 primary angioplasty, 2D/4D echocardiography, treadmill stress testing (ETT), Holter monitoring, and emergency coronary care unit (CCU) management.",
            iconName = "Favorite",
            headDoctor = "Dr. Tariq Rehman (FCPS, FACC)",
            services = listOf(
                "24/7 Emergency Cardiac Care & CCU",
                "Echocardiography (Adult & Pediatric)",
                "Angiography & Primary Angioplasty (PTCA)",
                "Exercise Tolerance Test (ETT / TMT)",
                "24-Hour Holter & BP Monitoring",
                "Pacemaker Implantation & Follow-up"
            ),
            opdTimings = "Mon - Sat: 09:00 AM - 05:00 PM",
            floorLocation = "Ground Floor, Heart Center Block B",
            activeBeds = 24,
            totalDoctors = 4
        ),
        Department(
            id = "gynecology",
            name = "Gynecology & Obstetrics",
            shortDescription = "Maternal-fetal medicine, painless delivery suites, high-risk pregnancy care, and laparoscopic surgery.",
            fullDescription = "Dedicated to women's comprehensive healthcare from adolescence through motherhood and menopause. Features modern modular labor suites, advanced fetal monitoring, NICU backup, and minimally invasive gynecological surgeries.",
            iconName = "Pregnant",
            headDoctor = "Dr. Samina Khan (FCPS)",
            services = listOf(
                "Antenatal & High-Risk Pregnancy Care",
                "Normal Delivery & Emergency C-Section Suites",
                "Laparoscopic Hysterectomy & Cystectomy",
                "Infertility Workup & Management",
                "Postnatal Care & Lactation Counseling",
                "Gynecological Oncology Screening"
            ),
            opdTimings = "Mon - Sat: 08:30 AM - 04:30 PM",
            floorLocation = "1st Floor, Mother & Child Wing",
            activeBeds = 32,
            totalDoctors = 5
        ),
        Department(
            id = "pediatrics",
            name = "Pediatrics & Neonatology",
            shortDescription = "Level-III Neonatal ICU, pediatric emergency, routine childhood immunizations, and developmental health.",
            fullDescription = "Our child health department is equipped with incubators, neonatal ventilators, phototherapy units, and round-the-clock pediatricians to ensure compassionate care for newborns, toddlers, and young adolescents across Swat.",
            iconName = "ChildCare",
            headDoctor = "Dr. Asfandyar Swati (MRCPCH)",
            services = listOf(
                "Level-III Neonatal Intensive Care Unit (NICU)",
                "Pediatric Emergency & Inpatient Care",
                "WHO & EPI Routine Immunization Center",
                "Growth, Nutrition & Developmental Assessment",
                "Pediatric Asthma & Allergy Clinic",
                "Phototherapy & Neonatal Jaundice Management"
            ),
            opdTimings = "Mon - Sat: 09:00 AM - 08:00 PM",
            floorLocation = "1st Floor, Pediatric Wing A",
            activeBeds = 28,
            totalDoctors = 4
        ),
        Department(
            id = "surgery",
            name = "General & Laparoscopic Surgery",
            shortDescription = "Minimally invasive keyhole procedures, acute trauma surgeries, oncologic and gastrointestinal operations.",
            fullDescription = "State-of-the-art modular operation theaters equipped with German HD laparoscopic towers, ultrasonic shears, and laminar airflow. Performed by experienced surgeons with quick post-op recovery protocols.",
            iconName = "Healing",
            headDoctor = "Dr. Muhammad Bilal (FRCS)",
            services = listOf(
                "Laparoscopic Cholecystectomy (Gallbladder)",
                "Laparoscopic & Open Hernia Repair",
                "Appendectomy (Keyhole / Laparoscopic)",
                "Gastrointestinal & Colorectal Resections",
                "Thyroid & Breast Surgery",
                "Emergency Trauma Laparotomy"
            ),
            opdTimings = "Mon - Sat: 10:00 AM - 06:00 PM",
            floorLocation = "2nd Floor, Surgical Suites Block",
            activeBeds = 35,
            totalDoctors = 5
        ),
        Department(
            id = "orthopedics",
            name = "Orthopedics & Trauma",
            shortDescription = "Total joint replacements, complex fractures, arthroscopy, spine care, and sports injury rehabilitation.",
            fullDescription = "Specializing in the treatment of bones, joints, ligaments, tendons, and muscles. From road accident trauma stabilization to elective knee and hip replacements and keyhole arthroscopic ligament reconstructions.",
            iconName = "Accessibility",
            headDoctor = "Dr. Zahir Shah (FCPS Ortho)",
            services = listOf(
                "Total Knee & Hip Arthroplasty (Joint Replacement)",
                "Complex Fracture Fixation with C-Arm Guidance",
                "Arthroscopic ACL / Meniscus Repair",
                "Spine Decompression & Disc Surgery",
                "Pediatric Bone & Deformity Correction",
                "Physiotherapy & Sports Injury Rehab"
            ),
            opdTimings = "Mon - Sat: 09:30 AM - 05:30 PM",
            floorLocation = "Ground Floor, Ortho Wing C",
            activeBeds = 30,
            totalDoctors = 4
        ),
        Department(
            id = "emergency",
            name = "Emergency & Critical Care (ICU)",
            shortDescription = "24/7 Red Code Trauma center, dedicated triage, adult & neonatal ICU with full ventilator support.",
            fullDescription = "Always open 24 hours a day, 365 days a year. Our emergency and trauma center in Saidu Sharif handles acute cardiac arrests, polytrauma, stroke, surgical emergencies, and toxicology with dedicated resuscitation bays.",
            iconName = "LocalHospital",
            headDoctor = "Dr. Naveed Alam (MRCEM)",
            services = listOf(
                "24/7 Resuscitation & Red Code Trauma Bays",
                "18-Bed Multi-Disciplinary Intensive Care Unit (ICU)",
                "Advanced Mechanical Ventilators & Monitors",
                "Rapid Response Ambulance Service (BLS / ALS)",
                "Bedside Ultrasound (FAST) & Emergency Lab",
                "24/7 On-Call Surgical & Medical Specialists"
            ),
            opdTimings = "Open 24/7 - Round the Clock",
            floorLocation = "Emergency Ground Entrance (Direct Ramp)",
            activeBeds = 40,
            totalDoctors = 8
        ),
        Department(
            id = "radiology",
            name = "Radiology & Diagnostic Imaging",
            shortDescription = "High-field 1.5T MRI, 128-slice Multidetector CT scanner, 4D Doppler ultrasound, and digital fluoroscopy.",
            fullDescription = "Offering precision diagnostic imaging with prompt digital reporting. Connected to PACS for immediate consultation across all hospital departments and specialist review.",
            iconName = "Visibility",
            headDoctor = "Dr. Ayesha Noor (DMRD, FCPS)",
            services = listOf(
                "1.5 Tesla High-Field MRI",
                "128-Slice Low-Dose Multi-Detector CT Scan",
                "4D Color Doppler Ultrasound & Echocardiography",
                "Digital X-Ray & Orthopantomography (OPG)",
                "Ultrasound-Guided Biopsies & Drainages",
                "Digital Mammography for Breast Screening"
            ),
            opdTimings = "24/7 for Emergency / OPD: 08:00 AM - 10:00 PM",
            floorLocation = "Basement Level 1, Diagnostics Center",
            activeBeds = 10,
            totalDoctors = 3
        ),
        Department(
            id = "nephrology",
            name = "Nephrology & Hemodialysis",
            shortDescription = "Modern hemodialysis unit with German machines, kidney disease diagnosis, and electrolyte management.",
            fullDescription = "Providing comprehensive kidney care including acute and maintenance hemodialysis in a sterile, monitored setting with reverse osmosis (RO) ultra-pure water plant.",
            iconName = "WaterDrop",
            headDoctor = "Dr. Irfan Ullah (FCPS Nephrology)",
            services = listOf(
                "Maintenance Hemodialysis (Morning & Evening Shifts)",
                "Acute Renal Failure & Continuous Dialysis (CRRT)",
                "Hypertension & Diabetic Nephropathy Management",
                "Arteriovenous (AV) Fistula Evaluation & Care",
                "Kidney Stone Workup & Preventive Nephrology",
                "Urinary Tract Infection & Glomerulonephritis"
            ),
            opdTimings = "Mon - Sat: 09:00 AM - 05:00 PM",
            floorLocation = "2nd Floor, Renal Center Wing",
            activeBeds = 16,
            totalDoctors = 3
        )
    )

    val doctors = listOf(
        Doctor(
            id = "doc_tariq",
            name = "Dr. Tariq Rehman",
            title = "Head of Cardiology & Interventional Cardiologist",
            departmentId = "cardiology",
            departmentName = "Cardiology & Cath Lab",
            degrees = "MBBS, FCPS (Cardiology), FACC (USA)",
            experienceYears = 18,
            consultationFee = 1500,
            availableDays = "Mon, Tue, Wed, Thu, Fri, Sat",
            availableSlots = listOf("09:30 AM", "10:30 AM", "11:30 AM", "03:00 PM", "04:30 PM"),
            rating = 4.9f,
            reviewsCount = 340,
            bio = "Dr. Tariq Rehman has performed over 4,500 successful coronary angioplasties and angiographies. Dedicated to advancing cardiac care in Swat valley.",
            roomNumber = "OPD Room 101, Ground Floor"
        ),
        Doctor(
            id = "doc_samina",
            name = "Dr. Samina Khan",
            title = "Consultant Gynecologist & Laparoscopic Surgeon",
            departmentId = "gynecology",
            departmentName = "Gynecology & Obstetrics",
            degrees = "MBBS, FCPS (Gynecology & Obstetrics), Dip. Laparoscopy",
            experienceYears = 15,
            consultationFee = 1400,
            availableDays = "Mon, Tue, Wed, Thu, Fri, Sat",
            availableSlots = listOf("09:00 AM", "10:00 AM", "11:30 AM", "01:00 PM", "03:30 PM"),
            rating = 4.9f,
            reviewsCount = 412,
            bio = "Specialist in complex high-risk pregnancies, maternal medicine, painless labor, and endoscopic fertility-preserving surgeries.",
            roomNumber = "OPD Room 204, 1st Floor"
        ),
        Doctor(
            id = "doc_asfandyar",
            name = "Dr. Asfandyar Swati",
            title = "Consultant Pediatrician & Neonatal Specialist",
            departmentId = "pediatrics",
            departmentName = "Pediatrics & Neonatology",
            degrees = "MBBS, DCH, MRCPCH (UK), Fellowship Neonatology",
            experienceYears = 12,
            consultationFee = 1200,
            availableDays = "Mon, Tue, Wed, Thu, Fri, Sat",
            availableSlots = listOf("10:00 AM", "11:00 AM", "02:00 PM", "04:00 PM", "06:00 PM"),
            rating = 4.8f,
            reviewsCount = 289,
            bio = "Experienced in premature infant resuscitation, childhood developmental disorders, asthma management, and critical neonatal intensive care.",
            roomNumber = "OPD Room 208, 1st Floor"
        ),
        Doctor(
            id = "doc_bilal",
            name = "Dr. Muhammad Bilal",
            title = "Consultant General & Minimally Invasive Surgeon",
            departmentId = "surgery",
            departmentName = "General & Laparoscopic Surgery",
            degrees = "MBBS, FCPS (Surgery), FRCS (Glasgow)",
            experienceYears = 16,
            consultationFee = 1500,
            availableDays = "Mon, Tue, Wed, Thu, Fri",
            availableSlots = listOf("10:30 AM", "12:00 PM", "03:00 PM", "05:00 PM"),
            rating = 4.9f,
            reviewsCount = 310,
            bio = "Pioneer of laparoscopic abdominal surgeries in Malakand division. Special interest in single-incision laparoscopic surgery and hernia reconstructions.",
            roomNumber = "OPD Room 302, 2nd Floor"
        ),
        Doctor(
            id = "doc_zahir",
            name = "Dr. Zahir Shah",
            title = "Consultant Orthopedic & Joint Replacement Surgeon",
            departmentId = "orthopedics",
            departmentName = "Orthopedics & Trauma",
            degrees = "MBBS, FCPS (Orthopedics), AO Trauma Fellow (Switzerland)",
            experienceYears = 14,
            consultationFee = 1300,
            availableDays = "Mon, Tue, Wed, Thu, Fri, Sat",
            availableSlots = listOf("09:30 AM", "11:00 AM", "01:30 PM", "04:30 PM"),
            rating = 4.8f,
            reviewsCount = 275,
            bio = "Renowned orthopedic surgeon specializing in pelvic trauma, total knee and hip replacements, and minimally invasive fracture care.",
            roomNumber = "OPD Room 108, Ground Floor"
        ),
        Doctor(
            id = "doc_naveed",
            name = "Dr. Naveed Alam",
            title = "Emergency Medicine Director & Critical Care Incharge",
            departmentId = "emergency",
            departmentName = "Emergency & Critical Care (ICU)",
            degrees = "MBBS, MCPS (Emergency Medicine), MRCEM (UK)",
            experienceYears = 11,
            consultationFee = 1200,
            availableDays = "Every Day (24/7 Coverage)",
            availableSlots = listOf("09:00 AM", "01:00 PM", "05:00 PM", "08:00 PM"),
            rating = 4.9f,
            reviewsCount = 198,
            bio = "Certified emergency care specialist trained in Advanced Trauma Life Support (ATLS) and disaster management response.",
            roomNumber = "Emergency Desk, Ground Floor"
        ),
        Doctor(
            id = "doc_ayesha",
            name = "Dr. Ayesha Noor",
            title = "Consultant Radiologist & Imaging Specialist",
            departmentId = "radiology",
            departmentName = "Radiology & Diagnostic Imaging",
            degrees = "MBBS, DMRD, FCPS (Radiology)",
            experienceYears = 13,
            consultationFee = 1200,
            availableDays = "Mon, Tue, Wed, Thu, Fri, Sat",
            availableSlots = listOf("09:00 AM", "11:00 AM", "01:00 PM", "03:00 PM"),
            rating = 4.8f,
            reviewsCount = 164,
            bio = "Specializing in neuro-imaging, musculoskeletal MRI, obstetric 4D Doppler sonology, and image-guided biopsy procedures.",
            roomNumber = "Radiology Review Lab, Basement 1"
        ),
        Doctor(
            id = "doc_irfan",
            name = "Dr. Irfan Ullah",
            title = "Consultant Nephrologist & Renal Physician",
            departmentId = "nephrology",
            departmentName = "Nephrology & Hemodialysis",
            degrees = "MBBS, FCPS (Nephrology), ISN Fellow",
            experienceYears = 12,
            consultationFee = 1400,
            availableDays = "Mon, Tue, Wed, Thu, Fri, Sat",
            availableSlots = listOf("10:00 AM", "11:30 AM", "02:30 PM", "04:30 PM"),
            rating = 4.7f,
            reviewsCount = 182,
            bio = "Expert in dialysis therapy, glomerulonephritis management, diabetic kidney disease, and pre-transplant workup.",
            roomNumber = "OPD Room 308, 2nd Floor"
        )
    )

    val bloodStocks = listOf(
        BloodStock("O+", 26, "Available"),
        BloodStock("A+", 18, "Available"),
        BloodStock("B+", 22, "Available"),
        BloodStock("AB+", 9, "Available"),
        BloodStock("O-", 4, "Critical"),
        BloodStock("A-", 3, "Low Stock"),
        BloodStock("B-", 5, "Low Stock"),
        BloodStock("AB-", 2, "Critical")
    )

    val testimonials = listOf(
        Testimonial(
            id = "t1",
            patientName = "Sardar Ali Khan",
            location = "Saidu Sharif, Swat",
            rating = 5,
            department = "Cardiology & Cath Lab",
            comment = "When my father suffered acute chest pain at 2 AM, Rehman Hospital's emergency triage was instantaneous. Dr. Tariq Rehman performed life-saving angioplasty within 40 minutes. We are blessed to have such standard care right here in Swat.",
            date = "October 2026"
        ),
        Testimonial(
            id = "t2",
            patientName = "Shabana Gul",
            location = "Mingora, Swat",
            rating = 5,
            department = "Gynecology & Obstetrics",
            comment = "The maternity team and Dr. Samina Khan provided the warmest, most professional delivery care. The private suite was spotlessly clean and the nurses were so attentive.",
            date = "September 2026"
        ),
        Testimonial(
            id = "t3",
            patientName = "Ihsanullah Bacha",
            location = "Matta, Swat",
            rating = 5,
            department = "Orthopedics & Trauma",
            comment = "After a severe motorbike accident on Kalam highway, my tibia fracture was complicated. Dr. Zahir Shah performed precision surgery. Today I can walk comfortably again. Truly world-class hospital.",
            date = "August 2026"
        ),
        Testimonial(
            id = "t4",
            patientName = "Rashid Ahmad",
            location = "Kabal, Swat",
            rating = 5,
            department = "Pediatrics & NICU",
            comment = "Our newborn twin babies needed incubator care for 8 days in the NICU. Dr. Asfandyar Swati and the nursing staff cared for them like their own family. Heartfelt thanks!",
            date = "July 2026"
        )
    )

    val hospitalHighlights = listOf(
        "24/7 Red Code Emergency & Trauma Center",
        "Over 35 Board-Certified Medical & Surgical Specialists",
        "150+ Inpatient Bed Facility with Private & Executive Suites",
        "Dedicated Level-III Neonatal ICU & 18-Bed Adult CCU/ICU",
        "In-house 24/7 Blood Bank & Fully Automated Clinical Diagnostic Lab",
        "Emergency Ambulance Dispatch Fleet Covering Saidu Sharif & Greater Swat"
    )
}
