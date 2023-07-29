package com.cos.todaymeet.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;

import com.cos.todaymeet.MeetSort;
import com.cos.todaymeet.model.Meet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public abstract class MeetRepositoryImpl implements MeetRepository {

    public MeetRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <S extends Meet> S save(S entity) {
        entityManager.persist(entity);
        return entity;
    }
    @PersistenceContext
    private EntityManager entityManager;

    // ...

//    @Override
//    public EntityManager getEntityManager() {
//        return entityManager;
//    }

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends Meet> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Meet> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Meet> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Meet getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meet getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meet getReferenceById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Meet> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Meet> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Meet> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meet> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meet> findAllById(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<Meet> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Meet entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Meet> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Meet> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Meet> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Meet> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public <S extends Meet> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Meet> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Meet> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Meet, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meet findByMeetNo(int meetNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePhoneNumber(String phoneNumber, int userNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findTitleByMeetNo(int meetNo) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public String selectMeetByMeetNo(int meetNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Page<Object[]> findByAddressLike1(String address,String sort,Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meet> findByAddressContainingAndUser_UserNo(String address, int userNo) {
		// TODO Auto-generated method stub
		return null;
	}
//
//	@Override
//	public int findUserNoByMeetNo(int meetNo) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

//	@Override
//	public Object[] findByDetailMeetNo(int meetNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<MeetComment> selectCommentByMeetNo(int meetNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<UserDTO> findListMeetUser(int meetNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}

    // ...
}