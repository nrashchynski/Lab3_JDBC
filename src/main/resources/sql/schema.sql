-- Создание БД и выбор схемы
CREATE DATABASE IF NOT EXISTS developer_team_db
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;
USE developer_team_db;

-- Заказчики
CREATE TABLE IF NOT EXISTS customer (
  customer_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  contact_info VARCHAR(255)
) ENGINE=InnoDB;

-- Технические задания (ТЗ)
CREATE TABLE IF NOT EXISTS tech_spec (
  tech_spec_id INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT NOT NULL,
  title VARCHAR(150) NOT NULL,
  description TEXT,
  created_at DATE DEFAULT (CURRENT_DATE),
  CONSTRAINT fk_techspec_customer
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Проекты
CREATE TABLE IF NOT EXISTS project (
  project_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(120) NOT NULL,
  customer_id INT NOT NULL,
  tech_spec_id INT,
  status ENUM('NEW','IN_PROGRESS','COMPLETED','CANCELLED') DEFAULT 'NEW',
  cost DECIMAL(12,2) DEFAULT 0.00,
  CONSTRAINT fk_project_customer
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_project_techspec
    FOREIGN KEY (tech_spec_id) REFERENCES tech_spec(tech_spec_id)
    ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Разработчики
CREATE TABLE IF NOT EXISTS developer (
  developer_id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(120) NOT NULL,
  qualification ENUM('JUNIOR','MIDDLE','SENIOR','LEAD') NOT NULL,
  hourly_rate DECIMAL(10,2) NOT NULL CHECK (hourly_rate >= 0)
) ENGINE=InnoDB;

-- Назначения разработчиков на проекты
CREATE TABLE IF NOT EXISTS assignment (
  assignment_id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INT NOT NULL,
  developer_id INT NOT NULL,
  hours_worked INT DEFAULT 0 CHECK (hours_worked >= 0),
  assigned_at DATE DEFAULT (CURRENT_DATE),
  UNIQUE KEY uk_assignment_project_dev (project_id, developer_id),
  CONSTRAINT fk_assignment_project
    FOREIGN KEY (project_id) REFERENCES project(project_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_assignment_developer
    FOREIGN KEY (developer_id) REFERENCES developer(developer_id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- Счета по проектам
CREATE TABLE IF NOT EXISTS invoice (
  invoice_id INT AUTO_INCREMENT PRIMARY KEY,
  project_id INT NOT NULL,
  amount DECIMAL(12,2) NOT NULL CHECK (amount >= 0),
  is_paid BOOLEAN DEFAULT FALSE,
  issued_at DATE DEFAULT (CURRENT_DATE),
  paid_at DATE NULL,
  CONSTRAINT fk_invoice_project
    FOREIGN KEY (project_id) REFERENCES project(project_id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;
