package com.paulo.ticTacToe.messages;

import com.paulo.ticTacToe.messages.enums.MessageType;
import lombok.Data;

@Data
public class PlayerDisconnect {

    MessageType messageType = MessageType.DISCONNECT;
}
