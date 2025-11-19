import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	controlador.AllTestControlador.class,
	modelo.datos.AllTestDatos.class,
	modelo.negocio.EmpresaTestSuite.class,
	persistencia.AllTestPersistencia.class,
	vista.testGUI.AllTestGui.class,
})
public class AllTest {

}
