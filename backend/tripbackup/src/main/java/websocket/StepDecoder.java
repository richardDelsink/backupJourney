/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import domain.Journey;
import java.io.StringReader;
import java.util.Date;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import domain.Step;
import service.JourneyService;

public class StepDecoder implements Decoder.Text<Step> {

    @Inject
    private JourneyService journeyService;

    @Override
    public Step decode(final String textMessage) throws DecodeException {
        Step step = new Step();
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        step.setStepId(jsonObject.getInt("stepId"));
        step.setStepName(jsonObject.getString("stepName"));
        step.setLocation(jsonObject.getString("location"));
        step.setStory(jsonObject.getString("story"));
        step.setPostDate(new Date(jsonObject.getString("postDate")));
       // step.setJourney(journeyService.findByName(jsonObject.getString("journeyName")).getJourneyName());
        return step;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
