/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.Client;
import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author Collado Saez Miguel
 * @author Garcia Molina Joaquin
 *
 */
public class ReusablePoolTest {

	private ReusablePool pool;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool = ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pool = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		ReusablePool pool = ReusablePool.getInstance();
		// no es nulo
		assertNotNull(pool);
		// el objeto devuelto es ReusablePool
		assertTrue(pool instanceof ReusablePool);
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * @throws NotFreeInstanceException 
	 */
	@Test
	public void testAcquireReusable() throws NotFreeInstanceException {
		Reusable r1 = null, r2 = null, r3 = null;

		// Hay espacio para 2, (size=2 por defecto en ReusablePool)
		r1 = pool.acquireReusable();
		r2 = pool.acquireReusable();

		assertNotNull(r1);
		assertTrue(r1 instanceof Reusable);

		assertNotNull(r2);
		assertTrue(r2 instanceof Reusable);

		// Ya no queda espacio libre
		try {
			r3 = pool.acquireReusable();
		} catch (Exception e) {

			assertTrue(e instanceof NotFreeInstanceException);
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * @throws NotFreeInstanceException 
	 * @throws DuplicatedInstanceException 
	 */
	@Test
	public void testReleaseReusable() throws NotFreeInstanceException, DuplicatedInstanceException {
		Reusable r1 = null, r2 = null;
		
		r1 = pool.acquireReusable();

		// Liberamos un reusable
		pool.releaseReusable(r1);

		// Intentamos liberal un reusable que ya existe en el pool
		try {
			pool.releaseReusable(r1);
		} catch (Exception e) {
			assertTrue(e instanceof DuplicatedInstanceException);
		}
	}
	
	/**
	 * Test method for  {@link ubu.gii.dass.c01.Client#main(ubu.gii.dass.c01.Client)}.
	 * @throws DuplicatedInstanceException 
	 * @throws NotFreeInstanceException 
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testClient() throws NotFreeInstanceException, DuplicatedInstanceException {
		// Test constructor por defecto de Client.
		Client client = new Client();
		assertNotNull(client);
		assertTrue(client instanceof Client);
		
		// Test ejecucion main().
		client.main(null);
	}

}
