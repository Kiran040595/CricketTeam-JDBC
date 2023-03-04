/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */
package com.example.player.service;

import com.example.player.repository.PlayerRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import com.example.player.model.PlayerRowMapper;
import com.example.player.model.Player;

@Service
public class PlayerH2Service implements PlayerRepository{
    @Autowired
    private JdbcTemplate cricketDB;
    
    @Override
    public ArrayList<Player> getPlayers(){
        List<Player> playerList = cricketDB.query("select * from TEAM",new PlayerRowMapper());

        ArrayList<Player> players = new ArrayList<>(playerList);
             return players;

    }

    @Override
    public Player getPlayerById(int playerId){

        try{
        Player player = cricketDB.queryForObject("select * from TEAM where playerId=?", new PlayerRowMapper(), playerId);
        return player;
        }
        catch(Exception e){ 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
    }
    @Override
    public Player addPlayer(Player player){
        cricketDB.update( "insert into TEAM(playerName,jerseyNumber,role)values(?,?,?)",player.getPlayerName(),player.getJerseyNumber(),player.getRole());
        
        Player savedPlayer = cricketDB.queryForObject("select * from TEAM where playerName=? and jerseyNumber=? and role=?",new PlayerRowMapper(),player.getPlayerName(),player.getJerseyNumber(),player.getRole());
        return savedPlayer;
    }

    @Override    
    public Player updatePlayer(int playerId, Player player){
    try{
        Player existingPlayer = cricketDB.queryForObject("select * from TEAM where playerId=?", new PlayerRowMapper(), playerId);
        
        }
        catch(Exception e){ 
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }


        if(player.getPlayerName() != null){
            cricketDB.update("update TEAM set playerName =? where playerId=?",player.getPlayerName(),playerId);
        }
        if(player.getJerseyNumber() != 0){
            cricketDB.update("update TEAM set jerseyNumber = ? where playerId=?",player.getJerseyNumber(),playerId);
        }

        if(player.getRole() != null){
            cricketDB.update("update TEAM set role =? where playerId=?",player.getRole(),playerId);
        }

        return getPlayerById(playerId);
        
    }

    @Override
    public void deletePlayer(int playerId){

        // try{
        // Player existingPlayer = cricketDB.queryForObject("select * from TEAM where playerId=?", new PlayerRowMapper(), playerId);
        
        // }
        // catch(Exception e){ 
        //     throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        //}
        cricketDB.update("delete from TEAM where playerId=?",playerId);
            // throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    

       
        
    }
    

}




// Write your code here