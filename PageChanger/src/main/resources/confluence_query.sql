SELECT h.surname, h.firstname, p.project_name FROM human AS h
JOIN project AS p
ON h.project_id = p.id
ORDER BY h.surname