package uk.co.samwho.whobot.listeners;

import com.google.common.flogger.FluentLogger;
import com.google.inject.Singleton;
import net.dv8tion.jda.client.events.call.CallCreateEvent;
import net.dv8tion.jda.client.events.call.CallDeleteEvent;
import net.dv8tion.jda.client.events.call.GenericCallEvent;
import net.dv8tion.jda.client.events.call.update.CallUpdateRegionEvent;
import net.dv8tion.jda.client.events.call.update.CallUpdateRingingUsersEvent;
import net.dv8tion.jda.client.events.call.update.GenericCallUpdateEvent;
import net.dv8tion.jda.client.events.call.voice.*;
import net.dv8tion.jda.client.events.group.*;
import net.dv8tion.jda.client.events.group.update.GenericGroupUpdateEvent;
import net.dv8tion.jda.client.events.group.update.GroupUpdateIconEvent;
import net.dv8tion.jda.client.events.group.update.GroupUpdateNameEvent;
import net.dv8tion.jda.client.events.group.update.GroupUpdateOwnerEvent;
import net.dv8tion.jda.client.events.message.group.*;
import net.dv8tion.jda.client.events.message.group.react.GenericGroupMessageReactionEvent;
import net.dv8tion.jda.client.events.message.group.react.GroupMessageReactionAddEvent;
import net.dv8tion.jda.client.events.message.group.react.GroupMessageReactionRemoveAllEvent;
import net.dv8tion.jda.client.events.message.group.react.GroupMessageReactionRemoveEvent;
import net.dv8tion.jda.client.events.relationship.*;
import net.dv8tion.jda.core.events.*;
import net.dv8tion.jda.core.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.core.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.core.events.channel.category.GenericCategoryEvent;
import net.dv8tion.jda.core.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.core.events.channel.category.update.CategoryUpdatePermissionsEvent;
import net.dv8tion.jda.core.events.channel.category.update.CategoryUpdatePositionEvent;
import net.dv8tion.jda.core.events.channel.category.update.GenericCategoryUpdateEvent;
import net.dv8tion.jda.core.events.channel.priv.PrivateChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.priv.PrivateChannelDeleteEvent;
import net.dv8tion.jda.core.events.channel.text.GenericTextChannelEvent;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.core.events.channel.text.update.*;
import net.dv8tion.jda.core.events.channel.voice.GenericVoiceChannelEvent;
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.core.events.channel.voice.update.*;
import net.dv8tion.jda.core.events.emote.EmoteAddedEvent;
import net.dv8tion.jda.core.events.emote.EmoteRemovedEvent;
import net.dv8tion.jda.core.events.emote.GenericEmoteEvent;
import net.dv8tion.jda.core.events.emote.update.EmoteUpdateNameEvent;
import net.dv8tion.jda.core.events.emote.update.EmoteUpdateRolesEvent;
import net.dv8tion.jda.core.events.emote.update.GenericEmoteUpdateEvent;
import net.dv8tion.jda.core.events.guild.*;
import net.dv8tion.jda.core.events.guild.member.*;
import net.dv8tion.jda.core.events.guild.update.*;
import net.dv8tion.jda.core.events.guild.voice.*;
import net.dv8tion.jda.core.events.http.HttpRequestEvent;
import net.dv8tion.jda.core.events.message.*;
import net.dv8tion.jda.core.events.message.guild.*;
import net.dv8tion.jda.core.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveAllEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.message.priv.*;
import net.dv8tion.jda.core.events.message.priv.react.GenericPrivateMessageReactionEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.priv.react.PrivateMessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveAllEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.core.events.role.GenericRoleEvent;
import net.dv8tion.jda.core.events.role.RoleCreateEvent;
import net.dv8tion.jda.core.events.role.RoleDeleteEvent;
import net.dv8tion.jda.core.events.role.update.*;
import net.dv8tion.jda.core.events.self.*;
import net.dv8tion.jda.core.events.user.*;
import net.dv8tion.jda.core.events.user.update.*;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Log ALL of the events!
 */
@Singleton
public final class EventLogger extends ListenerAdapter {
    private static FluentLogger logger = FluentLogger.forEnclosingClass();

    @Override
    public void onGenericEvent(Event event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericUpdate(UpdateEvent<?, ?> event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onReady(ReadyEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onResume(ResumedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onReconnect(ReconnectedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onDisconnect(DisconnectEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onShutdown(ShutdownEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onStatusChange(StatusChangeEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onException(ExceptionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserNameUpdate(UserNameUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserAvatarUpdate(UserAvatarUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserOnlineStatusUpdate(UserOnlineStatusUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserGameUpdate(UserGameUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserUpdateName(UserUpdateNameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserUpdateDiscriminator(UserUpdateDiscriminatorEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserUpdateAvatar(UserUpdateAvatarEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserUpdateOnlineStatus(UserUpdateOnlineStatusEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserUpdateGame(UserUpdateGameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserTyping(UserTypingEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onSelfUpdateAvatar(SelfUpdateAvatarEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onSelfUpdateEmail(SelfUpdateEmailEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onSelfUpdateMFA(SelfUpdateMFAEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onSelfUpdateName(SelfUpdateNameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onSelfUpdateVerified(SelfUpdateVerifiedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMessageUpdate(GuildMessageUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMessageDelete(GuildMessageDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMessageEmbed(GuildMessageEmbedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMessageReactionRemove(GuildMessageReactionRemoveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMessageReactionRemoveAll(GuildMessageReactionRemoveAllEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onPrivateMessageUpdate(PrivateMessageUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onPrivateMessageDelete(PrivateMessageDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onPrivateMessageEmbed(PrivateMessageEmbedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onPrivateMessageReactionAdd(PrivateMessageReactionAddEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onPrivateMessageReactionRemove(PrivateMessageReactionRemoveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onMessageUpdate(MessageUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onMessageBulkDelete(MessageBulkDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onMessageEmbed(MessageEmbedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onMessageReactionRemoveAll(MessageReactionRemoveAllEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onTextChannelUpdateName(TextChannelUpdateNameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onTextChannelUpdateTopic(TextChannelUpdateTopicEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onTextChannelUpdatePosition(TextChannelUpdatePositionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onTextChannelUpdatePermissions(TextChannelUpdatePermissionsEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onTextChannelUpdateNSFW(TextChannelUpdateNSFWEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onTextChannelUpdateParent(TextChannelUpdateParentEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onTextChannelCreate(TextChannelCreateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onVoiceChannelUpdateName(VoiceChannelUpdateNameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onVoiceChannelUpdatePosition(VoiceChannelUpdatePositionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onVoiceChannelUpdateUserLimit(VoiceChannelUpdateUserLimitEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onVoiceChannelUpdateBitrate(VoiceChannelUpdateBitrateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onVoiceChannelUpdatePermissions(VoiceChannelUpdatePermissionsEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onVoiceChannelUpdateParent(VoiceChannelUpdateParentEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCategoryDelete(CategoryDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCategoryUpdateName(CategoryUpdateNameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCategoryUpdatePosition(CategoryUpdatePositionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCategoryUpdatePermissions(CategoryUpdatePermissionsEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCategoryCreate(CategoryCreateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onPrivateChannelCreate(PrivateChannelCreateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onPrivateChannelDelete(PrivateChannelDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildAvailable(GuildAvailableEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUnavailable(GuildUnavailableEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUnavailableGuildJoined(UnavailableGuildJoinedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildBan(GuildBanEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUnban(GuildUnbanEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateAfkChannel(GuildUpdateAfkChannelEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateSystemChannel(GuildUpdateSystemChannelEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateAfkTimeout(GuildUpdateAfkTimeoutEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateExplicitContentLevel(GuildUpdateExplicitContentLevelEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateIcon(GuildUpdateIconEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateMFALevel(GuildUpdateMFALevelEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateName(GuildUpdateNameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateNotificationLevel(GuildUpdateNotificationLevelEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateOwner(GuildUpdateOwnerEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateRegion(GuildUpdateRegionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateSplash(GuildUpdateSplashEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateVerificationLevel(GuildUpdateVerificationLevelEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildUpdateFeatures(GuildUpdateFeaturesEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildMemberNickChange(GuildMemberNickChangeEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceMute(GuildVoiceMuteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceDeafen(GuildVoiceDeafenEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceGuildMute(GuildVoiceGuildMuteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceGuildDeafen(GuildVoiceGuildDeafenEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceSelfMute(GuildVoiceSelfMuteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceSelfDeafen(GuildVoiceSelfDeafenEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGuildVoiceSuppress(GuildVoiceSuppressEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onRoleCreate(RoleCreateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onRoleUpdateColor(RoleUpdateColorEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onRoleUpdateHoisted(RoleUpdateHoistedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onRoleUpdateMentionable(RoleUpdateMentionableEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onRoleUpdateName(RoleUpdateNameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onRoleUpdatePermissions(RoleUpdatePermissionsEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onRoleUpdatePosition(RoleUpdatePositionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onEmoteAdded(EmoteAddedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onEmoteRemoved(EmoteRemovedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onEmoteUpdateName(EmoteUpdateNameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onEmoteUpdateRoles(EmoteUpdateRolesEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onHttpRequest(HttpRequestEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericMessage(GenericMessageEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericMessageReaction(GenericMessageReactionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGuildMessage(GenericGuildMessageEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGuildMessageReaction(GenericGuildMessageReactionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericPrivateMessage(GenericPrivateMessageEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericPrivateMessageReaction(GenericPrivateMessageReactionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericUser(GenericUserEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericUserPresence(GenericUserPresenceEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericSelfUpdate(GenericSelfUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericTextChannel(GenericTextChannelEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericTextChannelUpdate(GenericTextChannelUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericVoiceChannel(GenericVoiceChannelEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericVoiceChannelUpdate(GenericVoiceChannelUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericCategory(GenericCategoryEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericCategoryUpdate(GenericCategoryUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGuild(GenericGuildEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGuildUpdate(GenericGuildUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGuildMember(GenericGuildMemberEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGuildVoice(GenericGuildVoiceEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericRole(GenericRoleEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericRoleUpdate(GenericRoleUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericEmote(GenericEmoteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericEmoteUpdate(GenericEmoteUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onFriendAdded(FriendAddedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onFriendRemoved(FriendRemovedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserBlocked(UserBlockedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onUserUnblocked(UserUnblockedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onFriendRequestSent(FriendRequestSentEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onFriendRequestCanceled(FriendRequestCanceledEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onFriendRequestReceived(FriendRequestReceivedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onFriendRequestIgnored(FriendRequestIgnoredEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupJoin(GroupJoinEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupLeave(GroupLeaveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupUserJoin(GroupUserJoinEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupUserLeave(GroupUserLeaveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupMessageReceived(GroupMessageReceivedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupMessageUpdate(GroupMessageUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupMessageDelete(GroupMessageDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupMessageEmbed(GroupMessageEmbedEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupMessageReactionAdd(GroupMessageReactionAddEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupMessageReactionRemove(GroupMessageReactionRemoveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupMessageReactionRemoveAll(GroupMessageReactionRemoveAllEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupUpdateIcon(GroupUpdateIconEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupUpdateName(GroupUpdateNameEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGroupUpdateOwner(GroupUpdateOwnerEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCallCreate(CallCreateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCallDelete(CallDeleteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCallUpdateRegion(CallUpdateRegionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCallUpdateRingingUsers(CallUpdateRingingUsersEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCallVoiceJoin(CallVoiceJoinEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCallVoiceLeave(CallVoiceLeaveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCallVoiceSelfMute(CallVoiceSelfMuteEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onCallVoiceSelfDeafen(CallVoiceSelfDeafenEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericRelationship(GenericRelationshipEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericRelationshipAdd(GenericRelationshipAddEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericRelationshipRemove(GenericRelationshipRemoveEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGroup(GenericGroupEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGroupMessage(GenericGroupMessageEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGroupMessageReaction(GenericGroupMessageReactionEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericGroupUpdate(GenericGroupUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericCall(GenericCallEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericCallUpdate(GenericCallUpdateEvent event) {
        logger.atInfo().log(event.toString());
    }

    @Override
    public void onGenericCallVoice(GenericCallVoiceEvent event) {
        logger.atInfo().log(event.toString());
    }
}
