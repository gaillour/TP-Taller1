package persistencia;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	EmpresaDTOTest.class,
	UtilPersistenciaTest.class,
	PersistenciaBinTest_Esc1.class,
	PersistenciaBinTest_Esc2.class,
	PersistenciaBin_Esc3.class
})

public class AllTestPersistencia {
}
