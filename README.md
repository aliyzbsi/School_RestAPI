API Dokümantasyonu:

1. Öğrenci Yönetimi (Student Management)
   // GET /api/students - Tüm öğrencileri listele
   // GET /api/students/{id} - ID'ye göre öğrenci getir
   // POST /api/students - Yeni öğrenci ekle
   // PUT /api/students/{id} - Öğrenci bilgilerini güncelle
   // DELETE /api/students/{id} - Öğrenci kaydını sil
2. Öğretmen Yönetimi (Teacher Management)
   // GET /api/teachers - Tüm öğretmenleri listele
   // GET /api/teachers/{id} - ID'ye göre öğretmen getir
   // POST /api/teachers - Yeni öğretmen ekle
   // PUT /api/teachers/{id} - Öğretmen bilgilerini güncelle
   // DELETE /api/teachers/{id} - Öğretmen kaydını sil
3. Ders Yönetimi (Course Management)
   // GET /api/courses - Tüm dersleri listele
   // GET /api/courses/{id} - ID'ye göre ders getir
   // POST /api/courses - Yeni ders ekle
   // PUT /api/courses/{id} - Ders bilgilerini güncelle
   // DELETE /api/courses/{id} - Ders kaydını sil

Örnek Endpoint Kullanımı:
1. Yeni Öğrenci Ekleme:
   {
   "student": {
   "id": 0,
   "firstName": "string",
   "lastName": "string",
   "gender": "MALE",
   "email": "stringstri",
   "salary": 2000
   },
   "courseList": [
   {
   "title": "string",
   "gpa": 0,
   "id": 0
   }
   ]
   }

