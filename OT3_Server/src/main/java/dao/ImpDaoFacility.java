package dao;

import entity.Facility;

import java.util.Map;

public interface ImpDaoFacility {
    public boolean updateFaccility(Facility facilityNewInfor);
    public Map<Facility,Long> countMaintenance();
}
