# CashFlow API

API REST de gestion de flux financiers (donations & dépenses) développée en **Java Spring Boot** avec **JDBC pur** (sans JPA) et **PostgreSQL**.

---

## Prérequis

- Java 21+
- Maven 3.8+
- PostgreSQL 14+
- Postman (pour tester les endpoints)

---

## Installation et lancement

### 1. Cloner le projet

```bash
git clone <url-du-repo>
cd cashflow
```

### 2. Créer la base de données PostgreSQL

```cmd
psql -U postgres -c "CREATE DATABASE cashflow_db;"
```

Puis se placer dans le dossier du projet et exécuter les scripts SQL :

```cmd
psql -U postgres -d cashflow_db -f sql/database.sql
psql -U postgres -d cashflow_db -f sql/inserts.sql
```

> **Windows** : si le chemin contient des espaces, utiliser `\i` depuis psql :
> ```sql
> \i 'C:/chemin/vers/cashflow/sql/database.sql'
> \i 'C:/chemin/vers/cashflow/sql/inserts.sql'
> ```

### 3. Configurer les variables d'environnement

Copier le fichier `.env.example` et le renommer en `.env` :

```bash
cp .env .env
```

Puis éditer le fichier `.env` avec vos propres valeurs
(Notez qu'ici on utilise postgres pour etre sur d'avoir l'acces sans restriction) 

```env
DB_URL=jdbc:postgresql://localhost:5432/cashflow_db
DB_USERNAME=postgres
DB_PASSWORD=votre_mot_de_passe_postgresql
```

> ⚠️ Le fichier `.env` est ignoré par Git (via `.gitignore`) — il ne sera jamais partagé.

### 4. Lancer l'application

```bash
mvn spring-boot:run
```

L'API sera disponible sur : `http://localhost:8080`

---

## Endpoints disponibles

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/cash-flows` | Tous les cash-flows |
| GET | `/cash-flows?type=donation` | Filtrer par type (`donation` ou `expense`) |
| GET | `/users/{id}/cash-flows` | Cash-flows d'un utilisateur |
| POST | `/expenses` | Créer une nouvelle dépense |
| GET | `/balance` | Bilan global (donations - dépenses) |

### Exemple POST /expenses

```json
{
  "userId": "u1-0000-0000-0000-000000000001",
  "amount": 75.50,
  "reason": "Achat fournitures",
  "frequency": "MONTHLY"
}
```

Valeurs possibles pour `frequency` : `NONE`, `MONTHLY`, `WEEKLY`, `YEARLY`

---

## Structure du projet

```
cashflow/
├── sql/
│   ├── database.sql        ← Schéma PostgreSQL
│   └── inserts.sql         ← Données de test
├── .env.example            ← Template de configuration (à copier en .env)
├── .gitignore
├── pom.xml
└── src/main/java/com/app/
    ├── model/              ← CashFlow, Donation, Expense, User, ExpenseFrequency
    ├── dto/                ← CashFlowResponse, CreateExpenseRequest, BalanceResponse
    ├── repository/         ← JDBC pur (DataSource → Connection → PreparedStatement)
    ├── service/
    └── controller/
```

---

## Choix techniques

- **JDBC pur** : aucun JPA/Hibernate, uniquement `DataSource` → `Connection` → `PreparedStatement`
- **Table per hierarchy** : une seule table `cash_flows` avec colonne discriminatrice `type`
- **Enum PostgreSQL** : `expense_frequency` défini comme type natif PostgreSQL
