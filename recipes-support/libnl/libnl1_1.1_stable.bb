DESCRIPTION = "libnl is a library for applications dealing with netlink sockets"
SECTION = "libs/network"

inherit autotools pkgconfig

LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=2b41e13261a330ee784153ecbb6a82bc"

SRC_URI = "git://github.com/tgraf/libnl-1.1-stable.git"

CLEANBROKEN = "1"

# tag: libnl1_1_4
SRCREV = "bf77c9d27a03a9eb38c391eb1af22c9f483261c0"

S = "${WORKDIR}/git"
B = "${S}"

INSANE_SKIP_${PN} += "ldflags"

SRC_URI[md5sum] = "9235961a47bac82f5a4bf66c914829d5"
SRC_URI[sha256sum] = "6fb2c1c3f466ae7b4a41bc0fd709960a07c343dc1723f8a6b098ef034d3c8638"
