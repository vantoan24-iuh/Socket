package dao;

import entity.Person;

import java.util.List;

public interface ImpDaoPerson {
    public List<Person> getMainanceWorkers(String facilityName);
}
