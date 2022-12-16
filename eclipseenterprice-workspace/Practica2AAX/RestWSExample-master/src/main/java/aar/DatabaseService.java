package aar;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.logging.Logger;

public class DatabaseService {

	Logger log = Logger.getLogger(DatabaseService.class.getName());

	public int insertEmployee(Employee employee) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(employee);
			entityManager.flush();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();

		}
		return employee.getId();
	}

	public int insertChat(Chat chat) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(chat);
			entityManager.flush();
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();
		}
		return chat.getId();
	}

	public Employee readEmployee(int id) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		Employee product = null;
		try {
			product = entityManager.find(Employee.class, id);
		} finally {
			entityManager.close();

			if (product == null)
				log.warning("No records records were found with given id value");

		}
		return product;
	}

	public Chat readChat(int id) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		Chat product = null;
		try {
			product = entityManager.find(Chat.class, id);
		} finally {
			entityManager.close();

			if (product == null)
				log.warning("No records records were found with given id value");

		}
		return product;
	}

	public boolean deleteEmployee(int id) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		boolean result = false;
		try {
			entityManager.getTransaction().begin();
			Employee entity = null;

			entity = entityManager.find(Employee.class, id);
			if (entity != null) {
				entityManager.remove(entity);
				entityManager.getTransaction().commit();
				result = true;
			} else {
				log.warning("No records records were found with given id value !!");
				result = false;
			}

		} catch (Exception e) {
			result = false;
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();

		}
		return result;
	}

	public boolean deleteChat(int id) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		boolean result = false;
		try {
			entityManager.getTransaction().begin();
			Chat entity = null;

			entity = entityManager.find(Chat.class, id);
			if (entity != null) {
				entityManager.remove(entity);
				entityManager.getTransaction().commit();
				result = true;
			} else {
				log.warning("No records records were found with given id value !!");
				result = false;
			}

		} catch (Exception e) {
			result = false;
			entityManager.getTransaction().rollback();
			throw e;
		} finally {
			entityManager.close();

		}
		return result;
	}

	public List<Employee> searchEmployee(String key, String value) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
			Root<Employee> root = criteriaQuery.from(Employee.class);
			criteriaQuery.select(root);
			Predicate where = criteriaBuilder.equal(root.get(key), value);
			criteriaQuery.where(where);
			return entityManager.createQuery(criteriaQuery).getResultList();
		} finally {
			entityManager.close();

		}
	}

	public List<Chat> searchChat(String key, String value) {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Chat> criteriaQuery = criteriaBuilder.createQuery(Chat.class);
			Root<Chat> root = criteriaQuery.from(Chat.class);
			criteriaQuery.select(root);
			Predicate where = criteriaBuilder.equal(root.get(key), value);
			criteriaQuery.where(where);
			return entityManager.createQuery(criteriaQuery).getResultList();
		} finally {
			entityManager.close();

		}
	}

	public List<Employee> findAllEmployees() {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

			Root<Employee> rootEntry = criteriaQuery.from(Employee.class);
			CriteriaQuery<Employee> all = criteriaQuery.select(rootEntry);
			TypedQuery<Employee> allQuery = entityManager.createQuery(all);
			return allQuery.getResultList();
		} finally {
			entityManager.close();

		}
	}

	public List<Chat> findAllChats() {
		EntityManager entityManager = EntityManagerListener.createEntityManager();
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Chat> criteriaQuery = criteriaBuilder.createQuery(Chat.class);

			Root<Chat> rootEntry = criteriaQuery.from(Chat.class);
			CriteriaQuery<Chat> all = criteriaQuery.select(rootEntry);
			TypedQuery<Chat> allQuery = entityManager.createQuery(all);
			return allQuery.getResultList();
		} finally {
			entityManager.close();

		}
	}
}
