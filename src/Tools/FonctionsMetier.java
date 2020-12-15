/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Entity.Etat;
import Entity.Ticket;
import Entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jbuffeteau
 */
public class FonctionsMetier implements IMetier
{
    @Override
    public User GetUnUser(String login, String mdp)
    {
        try {
            Connection cnx = ConnexionBDD.getCnx();
            ResultSet rs;
            PreparedStatement stm;
            stm = cnx.prepareStatement("Select idUser,nomUser, prenomUser,statutUser from users where (loginUser='"+login+"' and pwdUser='"+mdp+"' )");
            System.out.println(stm);
            rs = stm.executeQuery();
            while (rs.next())
            {
                User use = new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
       //return User;
       return null;
    }

    @Override
    public ArrayList<Ticket> GetAllTickets()
    {
        
        ArrayList<Ticket> lesTickets = new ArrayList<>();
       try {
           // SELECT idTicket,nomTicket,dateTicket,etats.nomEtat from etats,tickets where tickets.numEtat= etats.idEtat and numUser="+idUser
            Connection cnx = ConnexionBDD.getCnx();
            ResultSet rs;
            PreparedStatement stm;
            stm = cnx.prepareStatement("SELECT* from tickets");
            rs = stm.executeQuery();
            while (rs.next())
            {
                Ticket tic = new Ticket(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                lesTickets.add(tic);
            }
            rs.close();
            } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesTickets;
    }

    @Override
    public ArrayList<Ticket> GetAllTicketsByIdUser(int idUser)
    {
        
        ArrayList<Ticket> lesTickets = new ArrayList<>();
       try {
           // SELECT idTicket,nomTicket,dateTicket,etats.nomEtat from etats,tickets where tickets.numEtat= etats.idEtat and numUser="+idUser
            Connection cnx = ConnexionBDD.getCnx();
            ResultSet rs;
            PreparedStatement stm;
            stm = cnx.prepareStatement("SELECT idTicket,nomTicket,dateTicket,etats.nomEtat from etats,tickets where tickets.numEtat= etats.idEtat and numUser="+idUser);
            rs = stm.executeQuery();
            while (rs.next())
            {
                Ticket tic = new Ticket(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                lesTickets.add(tic);
            }
            rs.close();
            } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesTickets;
    }

    @Override
    public void InsererTicket(int idTicket, String nomTicket, String dateTicket, int idUser, int idEtat) 
    {
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("insert into tickets values (?,?,?,?,?)");
            ps.setInt(1, idTicket);
            ps.setString(2, nomTicket);
            ps.setString(3, dateTicket);
            ps.setInt(4, idUser);
            ps.setInt(5, idEtat);
            
            System.out.println(ps);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ModifierEtatTicket(int idTicket, int idEtat) 
    {
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement ps = cnx.prepareStatement("update tickets set numEtat = ? where idTicket = ?");
            ps.setInt(1, idEtat);
            ps.setInt(2, idTicket);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        // update tickets set numEtat where idTicket
    }

    @Override
    public ArrayList<User> GetAllUsers()
    {
        {   
       ArrayList<User> lesUsers = new ArrayList<>();
       try {
            Connection cnx = ConnexionBDD.getCnx();
            ResultSet rs;
            PreparedStatement stm;
            stm = cnx.prepareStatement("Select idUser,nomUser from users");
            rs = stm.executeQuery();
            while (rs.next())
            {
                User use = new User(rs.getInt(1),rs.getString(2));
                lesUsers.add(use);
            }
            rs.close();
            } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesUsers;
    }
    }

    @Override
    public int GetLastIdTicket()
    {
        int idTicket = 0;
        try {
            Connection cnx = ConnexionBDD.getCnx();
            PreparedStatement stm;
            stm = cnx.prepareStatement("select max(idTicket) from tickets");
            
            ResultSet rs = stm.executeQuery();
            
            rs.next();
            idTicket = rs.getInt(1)+ 1;
            rs.close();
     
            
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idTicket;
    }

    @Override
    public int GetIdEtat(String nomEtat)  // A voir 
    {
        try {
            Connection cnx = ConnexionBDD.getCnx();
            ResultSet rs;
            PreparedStatement stm;
            //stm = cnx.prepareStatement("SELECT etats.nomEtat from etats,ticket where (tickets.numEtat= etats.idEtat and nomEtat='"+nomEtat+"')");
            stm = cnx.prepareStatement("SELECT idEtat from etat where (nomEtat='"+nomEtat+"')");
            rs = stm.executeQuery();
            while (rs.next())
            {
                Etat e = new Etat(rs.getInt(1));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; 
    }

    @Override
    public ArrayList<Etat> GetAllEtats()
    {
        ArrayList<Etat> lesEtats = new ArrayList<>();
       try {
           
            Connection cnx = ConnexionBDD.getCnx();
            ResultSet rs;
            PreparedStatement stm;
            stm = cnx.prepareStatement("SELECT idEtat,nomEtat from etats");
            rs = stm.executeQuery();
            while (rs.next())
            {
                Etat e = new Etat(rs.getInt(1),rs.getString(2));
                lesEtats.add(e);
            }
            rs.close();
            } catch (SQLException ex) {
            Logger.getLogger(FonctionsMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lesEtats;
    }
}
