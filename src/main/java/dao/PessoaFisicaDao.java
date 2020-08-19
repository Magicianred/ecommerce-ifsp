package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Address;
import model.PessoaFisica;
import model.Phone;
import util.MysqlConection;

public class PessoaFisicaDao {
    private Connection con;

    public PessoaFisicaDao() {
        con = new MysqlConection().getConnection();
    }
    
    //Register normal customer, not an business
    //PessoaFísica extends User, so the attributer of user will be userd here as well
    public void registerPessoaFisica(PessoaFisica pf) throws SQLException {
        
        String query;
        //First - Insert User
        query = "INSERT INTO user (id_User, email, password, user_Tyoe, token) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement st = con.prepareStatement(query);
        st.setInt(1, pf.getIdUser());
        st.setString(2, pf.getEmail());
        st.setString(3, pf.getPassword());
        st.setInt(4, pf.getTypeOfUser());
        st.setString(5, pf.getToken());
        st.execute();

        //Second - Insert Customer 
        query = "INSERT INT customer(id_Customer, user_id_User) VALUES(?, ?)";
        st = con.prepareStatement(query);
        st.setInt(1, pf.getIdCustomer());
        st.setInt(2, pf.getIdUser());
        st.execute();

        //Third - Insert Pessoa Física 
        query = "INSERT INTO pessoa_Fisica(id_Pessoa_Fisica, name_Customer, CPF, RG, date_Birth, customer_id_Customer) VALUES (?, ?, ?, ?, ?, ?)";
        st = con.prepareStatement(query);
        st.setInt(1, pf.getIdPessoaFisica());
        st.setString(2, pf.getNameCustomer());
        st.setString(3, pf.getCPF());;
        st.setString(4, pf.getRG());
        st.setDate(5, pf.getDateOfBirth());
        st.setInt(6, pf.getCustomer_id_Customer());
        st.execute();
        
        //Fourth - Insert Address
        Address adr = new Address();
        query = "INSERT INTO address(id_Address, street, number, district, city, state, zip_Code, customer_id_Customer) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        st = con.prepareStatement(query);
        st.setInt(1, adr.getIdAdress());
        st.setString(2, adr.getStreet());
        st.setString(3, adr.getNumber());
        st.setString(4, adr.getDistrict());
        st.setString(5, adr.getCity());
        st.setString(6, adr.getState());
        st.setString(7, adr.getZipCode());
        st.setInt(8, adr.getCustomerIdCustomer());
        st.execute();

        //Fifth - Insert the Phone
        Phone ph = new Phone();
        query = "INSERT INTO phone(id_Phone, phone, customer_id_Customer) VALUES(?, ?, ?)";
        st = con.prepareStatement(query);
        st.setInt(1, ph.getIdPhone());
        st.setString(2, ph.getPhone());
        st.setInt(3, ph.getCustomerIdCustomer());
        st.execute();

        st.close();
        con.close();
         
    }

    //Listing PessoaFisica customer
    //Whithout the Address and Phone yet
    public List<PessoaFisica> search() throws SQLException, Exception {
        List<PessoaFisica> list = new ArrayList();
        String query = "SELECT * FROM pessoa_Fisica";
        PreparedStatement st = con.prepareStatement(query);

        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            PessoaFisica pf = new PessoaFisica();

            pf.setIdPessoaFisica(rs.getInt("id_Pessoa_Fisica"));
            pf.setNameCustomer(rs.getString("name_Customer"));
            pf.setCPF(rs.getString("CPF"));
            pf.setRG(rs.getString("RG"));
            pf.setDateOfBirth(rs.getDate("date_Birth"));
            pf.setCustomer_id_Customer(rs.getInt("customer_id_Customer"));

            list.add(pf);
        }
        return list;
    }

    //Search for a specific costumer
    public List<PessoaFisica> search(String name) throws SQLException, Exception {
        List<PessoaFisica> list = new ArrayList();
        String query = "SELECT * FROM pessoa_Fisica WHERE name_Customer '" + name + "'";
        PreparedStatement st = con.prepareStatement(query);

        ResultSet rs = st.executeQuery();
        while(rs.next()) {
            PessoaFisica pf = new PessoaFisica();

            pf.setIdPessoaFisica(rs.getInt("id_Pessoa_Fisica"));
            pf.setNameCustomer(rs.getString("name_Customer"));
            pf.setCPF(rs.getString("CPF"));
            pf.setRG(rs.getString("RG"));
            pf.setDateOfBirth(rs.getDate("date_Birth"));
            pf.setCustomer_id_Customer(rs.getInt("customer_id_Customer"));
            
            list.add(pf);
        }
        return list;
    }
}