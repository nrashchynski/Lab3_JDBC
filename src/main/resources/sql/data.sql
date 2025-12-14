USE developer_team_db;

-- Заказчики
INSERT INTO customer (name, contact_info) VALUES
  ('ООО Альфа', 'alpha@example.com'),
  ('ЗАО Бета', 'beta@example.com');

-- ТЗ
INSERT INTO tech_spec (customer_id, title, description) VALUES
  (1, 'CRM-система', 'Требуются backend разработчики, интеграции, отчеты'),
  (2, 'Мобильное приложение', 'Клиент-сервер, синхронизация, офлайн режим');

-- Проекты
INSERT INTO project (name, customer_id, tech_spec_id, status, cost) VALUES
  ('Alpha CRM', 1, 1, 'IN_PROGRESS', 0.00),
  ('Beta Mobile', 2, 2, 'NEW', 0.00);

-- Разработчики
INSERT INTO developer (full_name, qualification, hourly_rate) VALUES
  ('Иван Петров', 'SENIOR', 50.00),
  ('Мария Сидорова', 'MIDDLE', 35.00),
  ('Алексей Смирнов', 'JUNIOR', 20.00);

-- Назначения
INSERT INTO assignment (project_id, developer_id, hours_worked) VALUES
  (1, 1, 10),
  (1, 2, 8),
  (2, 3, 0);

-- Счета (по проектам)
INSERT INTO invoice (project_id, amount, is_paid) VALUES
  (1, 0.00, FALSE),
  (2, 0.00, FALSE);
