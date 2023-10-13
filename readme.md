
# Packet Reference

### How do they work?

The packet system works by having a C and S Packet on both client and server, 
so the CPacket id on the server is the same as the CPacket id on the client and vise versa. 

#### Server
The CPackets on server only use the method `receive` to receive the packet from the client. 
The SPackets on the server only use the method `send` to send the packet to the client.

#### Client
And for the client-side CPackets on server only use the method `send` to send the packet to the server.
While SPackets on the client only use the method `receive` to receive the packet from the server.

### How do I make a packet (Client side)?
The CPacket is the one your actually going to send to the server and the SPacket is the one that the
client receives from the server. That's why on the SPacket the `send` method is empty because it's just
waiting for the server to send the packet. And on the CPacket the `receive` method is empty because
it's just sending the packet to the server, we don't expect the server to respond with a CPacket.

```java
public class CPacketPing extends Packet {
    private static long currentTimeMillis;

    public CPacketPing(UUID connectionUUID) {
        super(connectionUUID);
    }

    public CPacketPing(long currentTimeMillis) {
        super(null);
        CPacketPing.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        // What we are sending to the server
        
        writingByteBuffer.writeLong(currentTimeMillis);
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
        // empty because we wont receive anything
    }
}
```

```java
public class SPacketPong extends Packet {
    private static long currentTimeMillis;

    public SPacketPong(UUID connectionUUID) {
        super(connectionUUID);
    }

    public SPacketPong(long currentTimeMillis) {
        super(null);
        SPacketPong.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        // empty because we are not sending anything
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
        // What we get back from the server
        
        currentTimeMillis = readingByteBuffer.readLong();

        MainClient.ping = (System.currentTimeMillis() - currentTimeMillis);
    }
}
```

### How do I make a packet (Server side)?
The SPacket is the one your actually going to send to the client and the CPacket is the one that the 
server receives from the client. That's why on the CPacket the `send` method is empty because its just 
waiting for the client to send the packet. And on the SPacket the `receive` method is empty because
it's just sending the packet to the client.

```java
public class CPacketPing extends Packet {
    public CPacketPing(UUID connectionUUID) {
        super(connectionUUID);
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        // empty because we are not sending anything
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
        // What we get from the client, you also send your SPacket here as a response
        
        long currentTimeMillis = readingByteBuffer.readLong();

        Server.sendToClient(new SPacketPong(currentTimeMillis), getConnectionUUID());
    }
}
```

```java
public class SPacketPong extends Packet {
    public static long currentTimeMillis;

    public SPacketPong(UUID connectionUUID) {
        super(connectionUUID);
    }

    public SPacketPong(long currentTimeMillis) {
        super(null);
        SPacketPong.currentTimeMillis = currentTimeMillis;
    }

    @Override
    public void send(WritingByteBuffer writingByteBuffer) {
        // Whatever your sending to the client
        writingByteBuffer.writeLong(currentTimeMillis);
    }

    @Override
    public void receive(ReadingByteBuffer readingByteBuffer) {
        // empty because we wont receive anything
    }
}
```


### Server Packets

| Packets                           | Description              | Occurrences                           |
|:----------------------------------|:-------------------------|:--------------------------------------|
| `SPacketUpdateUUID`               | Updates client UUID      | **Only Once**. On connection opening  |
| `SPacketProfile`                  | Set some profile info    | **Only Once**. On connection opening  |
| `SPacketCreateGroup`              | Creates a voice group    | Whenever the user makes a voice group |
| `SPacketRequestGroupList`         | Requests all group names | On request                            |

### Client Packets

| Packets                   | Description                  | Occurrences                              |
|:--------------------------|:-----------------------------|:-----------------------------------------|
| `CPacketPing`             | Client's ping to server      | **Every 1.5 seconds**. Always sends      |
| `CPacketDisconnect`       | When client is disconnecting | **On Disconnect**.                       |
| `CPacketProfile`          | Set some profile info        | **Only Once**. On connection opening     |
| `CPacketCreateGroup`      | Creates a voice group        | Whenever the user makes a voice group    |
| `CPacketRequestGroupList` | Requests all group names     | **On request**                           |

# Events & Event Listeners

### How do they work?

The event system has 3 types of events, `"Common"`, `"Server"`, and `"Client"`. 
The `"Common"` events are events that are used on both the server and client. 
The `"Server"` events are events that are only used on the server. 
And the `"Client"` events are events that are only used on the client.

Events are triggered by the `EventBus` class. To trigger an event you use the method `post`.
The `post` method takes in an event object, 

### How do I make a:

#### Common Event

```java
public class EventTest extends Event {
    private final String message;

    public EventTest(String message) { // this can be anything you want, it's just an example
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

#### Server Event

```java
@SideOnly(Side.Server)
public class EventTest extends Event {
    private final String message;

    public EventTest(String message) { // again, this can be anything you want, it's just an example
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

#### Client Event

```java
@SideOnly(Side.Client)
public class EventTest extends Event {
    private final String message;

    public EventTest(String message) { // once again, this can be anything you want, it's just an example
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
```

#### Event Listener

```java
public class ListenerTest {
    @SubscribeEvent
    public void onEventTest(EventTest event) {
        System.out.println(event.getMessage());
    }
}
```
 
# The Sound API

### The API

#### Audio Context
The `AudioContext` might just be the most important class of them all. This class initializes
the sound system, retries the default audio device and creates the context. The `init` method
is essential for the sound system to be able to operate. Thus, this method should be called at the
start of the program before you require sound. The `uninit` method uninitialized and must be
called after no more sound is required.

#### Sound Buffer
A `SoundBuffer` contains the raw audio data for Open AL. In here the frequency (sample rate) and the format
of the audio is stored.

#### Audio Source
An `AudioSource` is created of a `SoundBuffer`. This will be Open AL's operating base. Information
about position, gain and much more is stored in here and can be changed at runtime.

#### Audio Attributes
This class controls attributes of the underlying `AudioSource`. As already mentioned, an audio source
holds information about the music. This class controls this information. This means, you can get and
set information from here.

#### Audio Clip
An `AudioClip` ties it all together. This interface provides an `AudioSource` and an instance
of `AudioAttributes` to that audio source. Most importantly, the underlying music can be played,
stopped and discarded from here. 

### Audio Capturing

You can capture audio from `AudioCapture`. The capture methods of this return either a SoundBuffer
or an AudioSource. In case you don't want to play the audio but just want it to be sent it to the server,
(yes I am talking to you cj) you should use the method that returns a `SoundBuffer`.

### Examples

#### Audio Capture and Play

```java
public class AudioCaptureAndPlay {
    
    private static final int TIME_SECONDS = 5;
    
    public void start(){
        AudioCapture audioCapture = new AudioCapture(AUDIO_CONTEXT);
        SoundBuffer soundBuffer = audioCapture.capture(TIME_SECONDS);
        AudioSource audioSource = new AudioSource(soundBuffer);
        AudioClip audioClip = new SoundClip(AUDIO_CONTEXT, audioSource);
        System.out.println("Starting playback");
        audioClip.play();
        Thread.sleep(TIME_SECONDS * 1000);
        audioClip.stop();
        System.out.println("Stopped playback");
        // we cannot use that clip again after calling this method
        
        // audioClip.discard(); // this will discard the audio clip, currently not working.
        // System.out.println("Discarded clip");
    }
}
```

## Contributors

* **Thnks_CJ** - [GitHub Profile](https://github.com/ThnksCJ)
* **Cubic** - [GitHub Profile](https://github.com/Cuubicc)
