ALTER TABLE apartment
    ADD CHECK (city IN ('Ohrid', 'Skopje', 'Stip'));
ALTER TABLE apartment
    ADD CHECK (municipality IN ('Aerodrom', 'Aracinovo', 'Butel', 'Cair', 'Centar', 'Cucer-Sandevo', 'Gazi Baba',
                                'Gjorce Petrov', 'Ilinden', 'Karpos', 'Kisela Voda', 'Petrovec', 'Saraj', 'Sopiste',
                                'Studenicani', 'Suto Orizari', 'Zelenikovo', 'Ohrid', 'Stip'));
