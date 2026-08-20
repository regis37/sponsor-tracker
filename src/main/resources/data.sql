INSERT INTO companies (name, sector, city, created_by, created_at)
VALUES ('Siemens', 'INDUSTRY', 'Munich', 'Regis', '2026-08-01');

INSERT INTO companies (name, sector, city, created_by, created_at)
VALUES ('BMW Foundation', 'FOUNDATION', 'Munich', 'Sophie', '2026-08-02');

INSERT INTO events (name, event_date, target_budget, description)
VALUES ('Gala 2026', '2026-11-15', 5000.0, 'Gala annuel');

INSERT INTO events (name, event_date, target_budget, description)
VALUES ('Awards 2026', '2026-30-10', 15000.0, 'Ceremonie annuele');

INSERT INTO interactions (company_id, date, type, summary, outcome, next_action_date, next_action_note, created_by)
VALUES (1, '2026-08-01', 'EMAIL', 'Premier contact', 'TO_FOLLOW_UP', '2026-08-10', 'Relancer', 'Regis');
