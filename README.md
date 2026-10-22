# Ученици — CRUD

Необходими са JDK 21 или по-нов и работещ PostgreSQL.

1. Отворете pgAdmin и изпълнете веднъж.
CREATE TABLE students (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
2. В `StudentRepository.getConnection()` са адресът, потребителят и паролата
   за локалния PostgreSQL. При друг компютър променете тези три стойности.
3. В терминал в папката с pom.xml стартирайте:

```powershell                или            отворете Аpp.java
.\mvnw.cmd spring-boot:run                  Run --> Runwithout debugging
```

Отворете http://localhost:8080/ . Първото стартиране изисква интернет.

## Как минава заявката

HTML форма → Controller → Repository → PostgreSQL → Model → HTML.
Student съдържа само id и name. Repository съдържа SQL и JDBC.

GET методите за страниците връщат само името на HTML шаблона.
След операция връщаме директно същия шаблон с резултат в Model.
Името е задължително, до 100 символа. ID е положително цяло число.
При SQL грешка подробностите са в терминала, а на страницата има общо съобщение.
Проверете JDBC адреса, паролата и дали таблицата е в правилната база.