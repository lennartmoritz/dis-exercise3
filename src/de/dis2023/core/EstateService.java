package de.dis2023.core;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import de.dis2023.data.House;
import de.dis2023.data.Estate;
import de.dis2023.data.PurchaseContract;
import de.dis2023.data.EstateAgent;
import de.dis2023.data.TenancyContract;
import de.dis2023.data.Person;
import de.dis2023.data.Apartment;

/**
 *  Class for managing all database entities.
 * 
 * TODO: All data is currently stored in memory. 
 * The aim of the exercise is to gradually outsource data management to the 
 * database. When the work is done, all sets in this class become obsolete!
 */
public class EstateService {
	//TODO All these sets should be commented out after successful implementation.
	private Set<EstateAgent> estateAgents = new HashSet<EstateAgent>();
	private Set<Person> persons = new HashSet<Person>();
	private Set<House> houses = new HashSet<House>();
	private Set<Apartment> apartments = new HashSet<Apartment>();
	private Set<TenancyContract> tenancyContracts = new HashSet<TenancyContract>();
	private Set<PurchaseContract> purchaseContracts = new HashSet<PurchaseContract>();

	//Hibernate Session
	private SessionFactory sessionFactory;

	public EstateService() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	/**
	 * Updates a detached object in the DB with hibernate
	 * @param obj The instance to be updated
	 */
	public void updateInstance(Object obj) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.update(obj);
		session.getTransaction().commit();
	}

	public void refreshInstance(Object obj) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.refresh(obj);
		session.getTransaction().commit();
	}

	/**
	 * Find an estate agent with the given id
	 * @param id The ID of the agent
	 * @return Agent with ID or null
	 */
	public EstateAgent getEstateAgentByID(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		EstateAgent m = (EstateAgent) session.get(EstateAgent.class, id);
		session.getTransaction().commit();
		return m;
	}

	/**
	 * Find estate agent with the given login.
	 * @param login The login of the estate agent
	 * @return Estate agent with the given ID or null
	 */
	public EstateAgent getEstateAgentByLogin(String login) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String hql = "from EstateAgent as agent where agent.login = :a_login";
		EstateAgent m = (EstateAgent) session.createQuery(hql).setParameter("a_login", login).uniqueResult();
		session.getTransaction().commit();
		return m;
	}

	/**
	 * Returns all estateAgents
	 */
	public Set<EstateAgent> getAllEstateAgents() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String hql = "from EstateAgent";
		List<EstateAgent> agents = (List<EstateAgent>) session.createQuery(hql).list();
		Set<EstateAgent> agentsSet = new HashSet<>(agents);
		session.getTransaction().commit();
		return agentsSet;
	}

	/**
	 * Find an person with the given id
	 * @param id The ID of the person
	 * @return Person with ID or null
	 */
	public Person getPersonById(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Person p = (Person) session.get(Person.class, id);
		session.getTransaction().commit();
		return p;
	}

	/**
	 * Adds an estate agent
	 * @param ea The estate agent
	 */
	public void addEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(ea);
		session.getTransaction().commit();
	}

	/**
	 * Deletes an estate agent
	 * @param ea The estate agent
	 */
	public void deleteEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(ea);
		session.getTransaction().commit();
	}

	/**
	 * Adds a person
	 * @param p The person
	 */
	public void addPerson(Person p) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
	}

	/**
	 * Returns all persons
	 */
	public Set<Person> getAllPersons() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String hql = "from Person";
		List<Person> ret = (List<Person>) session.createQuery(hql).list();
		Set<Person> personSet = new HashSet<>(ret);
		session.getTransaction().commit();
		return personSet;
	}

	/**
	 * Deletes a person
	 * @param p The person
	 */
	public void deletePerson(Person p) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(p);
		session.getTransaction().commit();
	}

	/**
	 * Adds a house
	 * @param h The house
	 */
	public void addHouse(House h) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(h);
		session.getTransaction().commit();
	}

	/**
	 * Returns all houses of an estate agent
	 * @param ea the estate agent
	 * @return All houses managed by the estate agent
	 */
	public Set<House> getAllHousesForEstateAgent(EstateAgent ea) {
		Set<House> houses = new HashSet<>();
		refreshInstance(ea);
		for (Estate estate : ea.getEstates()) {
			if (estate instanceof House) {
				houses.add((House) estate);
			}
		}
		return houses;
	}

	/**
	 * Find a house with a given ID
	 * @param  id the house id
	 * @return The house or null if not found
	 */
	public House getHouseById(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		House h = (House) session.get(House.class, id);
		session.getTransaction().commit();
		return h;
	}

	/**
	 * Deletes a house
	 * @param h The house
	 */
	public void deleteHouse(House h) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(h);
		session.getTransaction().commit();
	}

	/**
	 * Adds an apartment
	 * @param w the aparment
	 */
	public void addApartment(Apartment w) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(w);
		session.getTransaction().commit();
	}

	/**
	 * Returns all apartments of an estate agent
	 * @param ea The estate agent
	 * @return All apartments managed by the estate agent
	 */
	public Set<Apartment> getAllApartmentsForEstateAgent(EstateAgent ea) {
		Set<Apartment> apartments = new HashSet<>();
		refreshInstance(ea);
		for (Estate estate : ea.getEstates()) {
			if (estate instanceof Apartment) {
				apartments.add((Apartment) estate);
			}
		}
		return apartments;
	}

	/**
	 * Find an apartment with given ID
	 * @param id The ID
	 * @return The apartment or zero, if not found
	 */
	public Apartment getApartmentByID(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Apartment a = (Apartment) session.get(Apartment.class, id);
		session.getTransaction().commit();
		return a;
	}

	/**
	 * Deletes an apartment
	 * @param p The apartment
	 */
	public void deleteApartment(Apartment w) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(w);
		session.getTransaction().commit();
	}


	/**
	 * Adds a tenancy contract
	 * @param t The tenancy contract
	 */
	public void addTenancyContract(TenancyContract t) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(t);
		session.getTransaction().commit();
	}

	/**
	 * Adds a purchase contract
	 * @param p The purchase contract
	 */
	public void addPurchaseContract(PurchaseContract p) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(p);
		session.getTransaction().commit();
	}

	/**
	 * Finds a tenancy contract with a given ID
	 * @param id Die ID
	 * @return The tenancy contract or zero if not found
	 */
	public TenancyContract getTenancyContractByID(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		TenancyContract c = (TenancyContract) session.get(TenancyContract.class, id);
		session.getTransaction().commit();
		return c;
	}

	/**
	 * Finds a purchase contract with a given ID
	 * @param id The id of the purchase contract
	 * @return The purchase contract or null if not found
	 */
	public PurchaseContract getPurchaseContractById(int id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		PurchaseContract c = (PurchaseContract) session.get(PurchaseContract.class, id);
		session.getTransaction().commit();
		return c;
	}

	/**
	 * Returns all tenancy contracts for apartments of the given estate agent
	 * @param m The estate agent
	 * @return All contracts belonging to apartments managed by the estate agent
	 */
	public Set<TenancyContract> getAllTenancyContractsForEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String sql = "SELECT tc.id FROM TenancyContract tc JOIN apartment ap ON tc.apartment_id = ap.id WHERE ap.manager = :m_id";
		List<Integer> ret = (List<Integer>) session.createNativeQuery(sql).setParameter("m_id", ea.getId()).list();
        		session.getTransaction().commit();
		Set<TenancyContract> contractSet = getAllTenancyContractsForIdList(ret);
		return contractSet;
	}

	public Set<TenancyContract> getAllTenancyContractsForIdList(List<Integer> ids) {
		Set<TenancyContract> contractSet = new HashSet<>();
		if (ids == null || ids.isEmpty()) {
			return contractSet;
		}
			Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String hql = "from TenancyContract where id in = (:c_ids)";
		List<TenancyContract> ret = (List<TenancyContract>) session.createQuery(hql).setParameterList("c_id", ids).list();
		session.getTransaction().commit();
		contractSet.addAll(ret);
		return contractSet;
	}

	/**
	 * Returns all purchase contracts for houses of the given estate agent
	 * @param m The estate agent
	 * @return All purchase contracts belonging to houses managed by the given estate agent
	 */
	public Set<PurchaseContract> getAllPurchaseContractsForEstateAgent(EstateAgent ea) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String sql = "SELECT pc.id FROM PurchaseContract pc JOIN house h ON pc.house_id = h.id WHERE h.manager = :m_id";
		List<Integer> ret = (List<Integer>) session.createNativeQuery(sql).setParameter("m_id", ea.getId()).list();
		session.getTransaction().commit();
		Set<PurchaseContract> contractSet = getAllPurchaseContractsForIdList(ret);
		return contractSet;
	}

	public Set<PurchaseContract> getAllPurchaseContractsForIdList(List<Integer> ids) {
		Set<PurchaseContract> contractSet = new HashSet<>();
		if (ids == null || ids.isEmpty()) {
			return contractSet;
		}
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		String hql = "from PurchaseContract where id in (:c_ids)";
		List<PurchaseContract> ret = (List<PurchaseContract>) session.createQuery(hql).setParameterList("c_ids", ids).list();
		session.getTransaction().commit();
		contractSet.addAll(ret);
		return contractSet;
	}


	/**
	 * Finds all tenancy contracts relating to the apartments of a given estate agent
	 * @param ea The estate agent
	 * @return set of tenancy contracts
	 */
	public Set<TenancyContract> getTenancyContractByEstateAgent(EstateAgent ea) {
		Set<TenancyContract> ret = new HashSet<TenancyContract>();
		Iterator<TenancyContract> it = tenancyContracts.iterator();

		while(it.hasNext()) {
			TenancyContract mv = it.next();

			if(mv.getApartment().getManager().getId() == ea.getId())
				ret.add(mv);
		}

		return ret;
	}

	/**
	 * Finds all purchase contracts relating to the houses of a given estate agent
	 * @param  ea The estate agent
	 * @return set of purchase contracts
	 */
	public Set<PurchaseContract> getPurchaseContractByEstateAgent(EstateAgent ea) {
		Set<PurchaseContract> ret = new HashSet<PurchaseContract>();
		Iterator<PurchaseContract> it = purchaseContracts.iterator();

		while(it.hasNext()) {
			PurchaseContract k = it.next();

			if(k.getHouse().getManager().getId() == ea.getId())
				ret.add(k);
		}

		return ret;
	}


	/**
	 * Deletes a tenancy contract
	 * @param tc the tenancy contract
	 */
	public void deleteTenancyContract(TenancyContract tc) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(tc);
		session.getTransaction().commit();
	}

	/**
	 * Deletes a purchase contract
	 * @param tc the purchase contract
	 */
	public void deletePurchaseContract(PurchaseContract pc) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(pc);
		session.getTransaction().commit();
	}

	/**
	 * Adds some test data
	 */
	public void addTestData() {
		//Hibernate Session erzeugen
		Session session = sessionFactory.openSession();

		session.beginTransaction();

		EstateAgent m = new EstateAgent();
		m.setName("Max Mustermann");
		m.setAddress("Am Informatikum 9");
		m.setLogin("max");
		m.setPassword("max");

		//TODO: This estate agent is kept in memory and the DB
		this.addEstateAgent(m);
		session.save(m);
		session.getTransaction().commit();

		session.beginTransaction();

		Person p1 = new Person();
		p1.setAddress("Informatikum");
		p1.setName("Mustermann");
		p1.setFirstname("Erika");


		Person p2 = new Person();
		p2.setAddress("Reeperbahn 9");
		p2.setName("Albers");
		p2.setFirstname("Hans");

		session.save(p1);
		session.save(p2);

		//TODO: These persons are kept in memory and the DB
		this.addPerson(p1);
		this.addPerson(p2);
		session.getTransaction().commit();


		session.beginTransaction();
		House h = new House();
		h.setCity("Hamburg");
		h.setPostalcode(22527);
		h.setStreet("Vogt-Kölln-Street");
		h.setStreetnumber("2a");
		h.setSquareArea(384);
		h.setFloors(5);
		h.setPrice(10000000);
		h.setGarden(true);
		h.setManager(m);

		session.save(h);

		//TODO: This house is held in memory and the DB
		this.addHouse(h);
		session.getTransaction().commit();

		// Create Hibernate Session
		session = sessionFactory.openSession();
		session.beginTransaction();
		EstateAgent m2 = (EstateAgent)session.get(EstateAgent.class, m.getId());
		Set<Estate> immos = m2.getEstates();
		Iterator<Estate> it = immos.iterator();

		while(it.hasNext()) {
			Estate i = it.next();
			System.out.println("Estate: "+i.getCity());
		}
		session.close();

		Apartment w = new Apartment();
		w.setCity("Hamburg");
		w.setPostalcode(22527);
		w.setStreet("Vogt-Kölln-Street");
		w.setStreetnumber("3");
		w.setSquareArea(120);
		w.setFloor(4);
		w.setRent(790);
		w.setKitchen(true);
		w.setBalcony(false);
		w.setManager(m);
		this.addApartment(w);

		w = new Apartment();
		w.setCity("Berlin");
		w.setPostalcode(22527);
		w.setStreet("Vogt-Kölln-Street");
		w.setStreetnumber("3");
		w.setSquareArea(120);
		w.setFloor(4);
		w.setRent(790);
		w.setKitchen(true);
		w.setBalcony(false);
		w.setManager(m);
		this.addApartment(w);

		PurchaseContract pc = new PurchaseContract();
		pc.setHouse(h);
		pc.setContractPartner(p1);
		pc.setContractNo(9234);
		pc.setDate(new Date(System.currentTimeMillis()));
		pc.setPlace("Hamburg");
		pc.setNoOfInstallments(5);
		pc.setIntrestRate(4);
		this.addPurchaseContract(pc);

		TenancyContract tc = new TenancyContract();
		tc.setApartment(w);
		tc.setContractPartner(p2);
		tc.setContractNo(23112);
		tc.setDate(new Date(System.currentTimeMillis()-1000000000));
		tc.setPlace("Berlin");
		tc.setStartDate(new Date(System.currentTimeMillis()));
		tc.setAdditionalCosts(65);
		tc.setDuration(36);
		this.addTenancyContract(tc);
	}
}
