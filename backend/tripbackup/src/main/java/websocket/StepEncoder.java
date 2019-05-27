/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;

import domain.Journey;
import domain.Step;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class StepEncoder implements Encoder.Text<Step> {

    @Override
    public String encode(Step step) throws EncodeException {
        return Json.createObjectBuilder()
                .add("stepId", step.getStepId())
                .add("stepName", step.getStepName())
                .add("location", step.getLocation())
                .add("story", step.getStory())
                .add("postDate", step.getPostDate().toString())
                //.add("journeyId", step.getJourney().getJourneyId())
                .build().toString();

    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
}
