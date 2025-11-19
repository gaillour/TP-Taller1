package vista.testGUI;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	TestGuiEscVacio.class,
	TestGuiEsc2.class,
	TestGuiEsc4.class,
	TestGuiAdmEscVacio.class,
	TestGuiAdmEsc2.class,
	TestGuiAdmEsc3.class,
	TestGuiAdmEsc5.class,
	TestEnabledDisabledEscVacio.class,
	TestEnabledDisabledEsc2.class,
	TestEnabledDisabledEsc3.class,
	TestEnabledDisabledEsc4.class,
	TestEnabledDisabledAdmEscVacio.class,
	TestEnabledDisabledAdmEsc3.class })
public class AllTestGui {
}