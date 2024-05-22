package dao;

import entity.Candidate;
import entity.Certificate;
import entity.Position;

import java.util.Map;
import java.util.Set;

public interface ImplCandidateDao {
    public Map<Candidate,Long> listCadidatesByCompanies();
    public Map<Candidate, Position> listCandidatesWithLongestWorking();
    public boolean addCandidate(Candidate candidate);
    public  Map<Candidate, Set<Certificate>> listCadidatesAndCertificates();
}
