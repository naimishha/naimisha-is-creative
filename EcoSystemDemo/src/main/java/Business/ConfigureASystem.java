package Business;

import Business.Employee.Employee;
import Business.Enterprise.Enterprise;
import Business.Enterprise.Enterprise.EnterpriseType;
import Business.Network.Network;
import Business.Organization.Organization;
import Business.Role.*;
import Business.UserAccount.UserAccount;

public class ConfigureASystem {

    public static EcoSystem configure() {
        EcoSystem system = EcoSystem.getInstance();

        // Create system admin
        Employee employee = system.getEmployeeDirectory().createEmployee("sysadmin");
        UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());

        // Create a network
        Network network = system.createAndAddNetwork();
        network.setName("Boston");

        // Create Hospital Enterprise
        Enterprise hospital = network.getEnterpriseDirectory().createAndAddEnterprise("Mass General", Enterprise.EnterpriseType.Hospital);

        // Create Doctor Organization and user
        Organization doctorOrg = hospital.getOrganizationDirectory().createOrganization(Organization.Type.Doctor);
        Employee doctorEmployee = doctorOrg.getEmployeeDirectory().createEmployee("Dr. Smith");
        doctorOrg.getUserAccountDirectory().createUserAccount("doctor", "doctor", doctorEmployee, new DoctorRole());

       // Create Diagnostic Labs Enterprise
        // Create Diagnostic Labs Enterprise
        Enterprise diagnosticLabs = network.getEnterpriseDirectory().createAndAddEnterprise("City Diagnostic Labs", Enterprise.EnterpriseType.DiagnosticLabs);

        // Create Lab Organization
        Organization labOrg = diagnosticLabs.getOrganizationDirectory().createOrganization(Organization.Type.Lab);

        // Create Lab Manager (Single login for all lab functions)
        Employee labManager = labOrg.getEmployeeDirectory().createEmployee("Lab Manager");
        labOrg.getUserAccountDirectory().createUserAccount("lab", "lab", labManager, new LabAssistantRole());



        // Create Pharmacy Organization and user
        Organization pharmacyOrg = hospital.getOrganizationDirectory().createOrganization(Organization.Type.Pharmacy);
        Employee pharmacyEmployee = pharmacyOrg.getEmployeeDirectory().createEmployee("Pharmacist");
        pharmacyOrg.getUserAccountDirectory().createUserAccount("pharmacy", "pharmacy", pharmacyEmployee, new PharmacyRole());

        // Create Admin Organization and user
        Organization adminOrg = hospital.getOrganizationDirectory().createOrganization(Organization.Type.Admin);
        Employee adminEmployee = adminOrg.getEmployeeDirectory().createEmployee("Admin User");
        adminOrg.getUserAccountDirectory().createUserAccount("admin", "admin", adminEmployee, new AdminRole());

        // Create Supplier Enterprise
        Enterprise supplierEnterprise = network.getEnterpriseDirectory().createAndAddEnterprise("SupplierCo", Enterprise.EnterpriseType.Supplier);

        // Create Supplier Organization and users
        Organization supplierOrg = supplierEnterprise.getOrganizationDirectory().createOrganization(Organization.Type.Supplier);

       // Add a single user for the Supplier Organization
Employee supplierEmployee = supplierOrg.getEmployeeDirectory().createEmployee("Supplier Manager");
supplierOrg.getUserAccountDirectory().createUserAccount("supplier", "supplier", supplierEmployee, new SupplierRole());
        return system;
    }
}
