package myprojects.fittdemo.repository;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.Member;
import myprojects.fittdemo.domain.Record;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {

    private final EntityManager em;

    public void save(Record record) {
        em.persist(record);
    }

    public Record findOne(Long recordId) {
        return em.find(Record.class, recordId);
    }

    public List<Record> findByMemberAndDate(Member member, LocalDate dateOfRecord) {
        return em.createQuery(
                "select r from Record r" +
                        " left join fetch r.trainingSessions" +
                        " where r.member = :member" +
                        " and r.dateOfRecord = :dateOfRecord" +
                        " and r.isRemoved = false", Record.class)
                .setParameter("member", member)
                .setParameter("dateOfRecord", dateOfRecord)
                .getResultList();

    }

    public List<Record> findAll(Member member) {
        return em.createQuery(
                "select r from Record r" +
                        " where r.member = :member" +
                        " and r.isRemoved = false", Record.class)
                .setParameter("member", member)
                .getResultList();
    }
}
