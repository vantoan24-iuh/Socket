package dao;

import entity.Position;

import java.util.List;
import java.util.Map;

public interface ImplPositionsDao {
    public List<Position> listPositions(String name,double salaryFrom, double salaryTo);
    public Map<Position, Integer> listYearsOfExperienceByPositionn(String candidateID);

    public Position getFindById(String id);
}
