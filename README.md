# WhispR

WhispR is a lightweight Java chat app that I made for fun because I got really bored one day,
and needed to find a smarter way to talk to my friends in other classes.
The chat rooms run locally, so as long as everyone is connected to the same
Wi-Fi network you'll be able to talk.

# How does it work?

WhispR utilizes Java's UDP Multicast Sockets from the `java.net` library to connect users to a
common port on a local access network. This port is chosen by the user as the "`Room Code`" and
allows multiple conversations to happen on different ports. Once users are connected to a room, their
messages are sent as packets for every listener on the network to view. WhispR has processes in place
to catch all UDP broadcasts on the port and display them onto a comprehensive user interface.