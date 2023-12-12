package controllers;

import akka.actor.ActorSystem;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;

import javax.inject.Inject;

public class WebSocketController extends Controller {
    private final ActorSystem actorSystem;

    @Inject
    public WebSocketController(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    public WebSocket socket() {
        return WebSocket.Text.accept(request ->
            actorSystem.actorOf(WebSocketActor.props())
        );
    }
}
