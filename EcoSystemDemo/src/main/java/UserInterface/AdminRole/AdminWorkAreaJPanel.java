package UserInterface.AdminRole;

import Business.EcoSystem;
import Business.Enterprise.Enterprise;
import Business.Organization.Organization;
import Business.UserAccount.UserAccount;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminWorkAreaJPanel extends JPanel {
    private JPanel container;
    private UserAccount userAccount;
    private Organization organization;
    private Enterprise enterprise;
    private EcoSystem system;
    
    private JTree enterpriseTree;
    private JButton btnAddEnterprise;
    private JButton btnAddOrganization;
    private JButton btnManageAdmin;
    private JComboBox<Enterprise.EnterpriseType> enterpriseTypeCombo;
    private JComboBox<Organization.Type> organizationTypeCombo;
    
    public AdminWorkAreaJPanel(JPanel container, UserAccount account, Organization organization, Enterprise enterprise, EcoSystem system) {
        this.container = container;
        this.userAccount = account;
        this.organization = organization;
        this.enterprise = enterprise;
        this.system = system;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Left Panel - Tree View
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Enterprise Hierarchy"));
        
        // Create root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Hospital Management System");
        createEnterpriseTree(root);
        enterpriseTree = new JTree(root);
        JScrollPane treeScroll = new JScrollPane(enterpriseTree);
        leftPanel.add(treeScroll, BorderLayout.CENTER);
        
        // Right Panel - Controls
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Enterprise Controls
        JPanel enterprisePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        enterpriseTypeCombo = new JComboBox<>(Enterprise.EnterpriseType.values());
        btnAddEnterprise = new JButton("Add Enterprise");
        enterprisePanel.add(new JLabel("Enterprise Type:"));
        enterprisePanel.add(enterpriseTypeCombo);
        enterprisePanel.add(btnAddEnterprise);
        
        // Organization Controls
        JPanel orgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        organizationTypeCombo = new JComboBox<>(Organization.Type.values());
        btnAddOrganization = new JButton("Add Organization");
        orgPanel.add(new JLabel("Organization Type:"));
        orgPanel.add(organizationTypeCombo);
        orgPanel.add(btnAddOrganization);
        
        // Admin Controls
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnManageAdmin = new JButton("Manage Administrators");
        adminPanel.add(btnManageAdmin);
        
        // Add panels to right panel
        rightPanel.add(enterprisePanel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(orgPanel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(adminPanel);
        
        // Add action listeners
        btnAddEnterprise.addActionListener(e -> addEnterprise());
        btnAddOrganization.addActionListener(e -> addOrganization());
        btnManageAdmin.addActionListener(e -> manageAdministrators());
        
        // Add main panels to frame
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }
    
    private void createEnterpriseTree(DefaultMutableTreeNode root) {
        // Add existing enterprises and their organizations
        for (Enterprise.EnterpriseType type : Enterprise.EnterpriseType.values()) {
            DefaultMutableTreeNode enterpriseNode = new DefaultMutableTreeNode(type.getValue());
            root.add(enterpriseNode);
            
            // Add organization types for each enterprise
            for (Organization.Type orgType : Organization.Type.values()) {
                enterpriseNode.add(new DefaultMutableTreeNode(orgType.getValue()));
            }
        }
    }
    
    private void addEnterprise() {
        Enterprise.EnterpriseType type = (Enterprise.EnterpriseType) enterpriseTypeCombo.getSelectedItem();
        // Add logic to create new enterprise
        refreshTree();
    }
    
    private void addOrganization() {
        Organization.Type type = (Organization.Type) organizationTypeCombo.getSelectedItem();
        // Add logic to create new organization
        refreshTree();
    }
    
    private void manageAdministrators() {
        // Add logic to manage administrators
    }
    
    private void refreshTree() {
        DefaultTreeModel model = (DefaultTreeModel) enterpriseTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();
        createEnterpriseTree(root);
        model.reload();
    }
} 