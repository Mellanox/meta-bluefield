#
# ex:ts=4:sw=4:sts=4:et
# -*- tab-width: 4; c-basic-offset: 4; indent-tabs-mode: nil -*-
#

SUMMARY = "IB Performance tests"
SECTION = "perftest"
LICENSE = "GPLv2|BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=9310aaac5cbd7408d794745420b94291"

COMPATIBLE_HOST = "aarch64.*-linux"

inherit autotools pkgconfig systemd

SRC_URI  = "https://github.com/linux-rdma/perftest/releases/download/v4.4-0.5/perftest-4.4-0.5.g1ceab48.tar.gz"
SRC_URI[md5sum] = "e8f98dea2f4d412ff17c210d6d8551b9"
SRC_URI[sha256sum] = "750999c9c8e6c066d06445aaa8a825fa578a273e8098c43c61dc4af4b707c2b1"

S = "${WORKDIR}/perftest-${PV}"

DEPENDS = "rdma-core glib-2.0"
RDEPENDS_${PN} = "rdma-core bash"

do_build[depends] += "glibc:do_populate_sysroot"
