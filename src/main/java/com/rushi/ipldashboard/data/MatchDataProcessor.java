package com.rushi.ipldashboard.data;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import com.rushi.ipldashboard.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

  private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

  @Override
  public Match process(final MatchInput matchInput){

    Match match = new Match();
    match.setId(Long.parseLong(matchInput.getId()));
    match.setCity(matchInput.getCity());
    
    match.setDate(LocalDate.parse(matchInput.getDate()));
    
    match.setPlayerOfMatch(matchInput.getPlayer_of_match());

    //Set Team 1 or Team 2 depending on the innings order
    String firstInningsTeam, secondInningsTeam;

    if("bat".equals(matchInput.getToss_decision())) {
        firstInningsTeam = matchInput.getToss_winner();
        secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) 
        ? matchInput.getTeam2() : matchInput.getTeam1();
    } else {
        secondInningsTeam = matchInput.getToss_winner();
        firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam2())
        ? matchInput.getTeam1() : matchInput.getTeam2();
    }
    match.setTeam1(firstInningsTeam);
    match.setTeam2(secondInningsTeam);

    match.setTossWinner(matchInput.getToss_winner());
    match.setTossDecision(match.getTossDecision());
    match.setMatchWinner(matchInput.getWinner());
    match.setResult(matchInput.getResult());
    match.setResultMargin(matchInput.getResult_margin());
    match.setUmpire1(matchInput.getUmpire1());
    match.setUmpire2(matchInput.getUmpire2());

    return match;
  }

}