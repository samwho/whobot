package uk.co.samwho.whobot.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import uk.co.samwho.whobot.listeners.EventLogger;
import uk.co.samwho.whobot.listeners.StreamingNowRoleAssigner;
import uk.co.samwho.whobot.listeners.SwearWordTracker;

public class ListenerModule extends AbstractModule {
    @Override
    public void configure() {
        Multibinder<ListenerAdapter> listeners = Multibinder.newSetBinder(binder(), ListenerAdapter.class);

        //listeners.addBinding().to(EventLogger.class);
        listeners.addBinding().to(SwearWordTracker.class);
        listeners.addBinding().to(StreamingNowRoleAssigner.class);
    }
}
