SUMMARY = "TCP / IP networking and traffic control utilities"
DESCRIPTION = "Iproute2 is a collection of utilities for controlling \
TCP / IP networking and traffic control in Linux.  Of the utilities ip \
and tc are the most important.  ip controls IPv4 and IPv6 \
configuration and tc stands for traffic control."
HOMEPAGE = "http://www.linuxfoundation.org/collaborate/workgroups/networking/iproute2"
SECTION = "base"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a \
                    file://ip/ip.c;beginline=3;endline=8;md5=689d691d0410a4b64d3899f8d6e31817"

DEPENDS = "flex-native bison-native iptables elfutils libcap libmnl"

inherit update-alternatives bash-completion pkgconfig

CLEANBROKEN = "1"

PACKAGECONFIG ??= "tipc"
PACKAGECONFIG[tipc] = ",,libmnl,"

export prefix="/opt/mellanox/iproute2"
export exec_prefix="/opt/mellanox/iproute2"

FILES_${PN} += "${prefix} /var/run/netns-mlnx"

EXTRA_OEMAKE = "CC='${CC}' KERNEL_INCLUDE=${STAGING_INCDIR} DOCDIR=${docdir}/mlnx-iproute2 PREFIX='${prefix}' SBINDIR='${sbindir}' LIBDIR='${libdir}' CONFDIR='${prefix}/etc' NETNS_RUN_DIR='/var/run/netns-mlnx' NETNS_ETC_DIR='${prefix}/etc/netns' ARPDIR='${prefix}/var/lib/arpd' SUBDIRS='lib ip tc bridge misc genl tipc devlink rdma'"

do_configure_append () {
    sh configure ${STAGING_INCDIR}
    # Explicitly disable ATM support
    sed -i -e '/TC_CONFIG_ATM/d' config.mk
	echo "HAVE_MNL:=y" >> config.mk
	echo 'CFLAGS += -DHAVE_LIBMNL' >> config.mk
	echo 'LDLIBS += -lmnl' >> config.mk
}

do_install () {
    oe_runmake DESTDIR=${D} install
}

do_unpack_extra() {
	cd ${WORKDIR}
	tar xzf mlnx-iproute2-${PV}.tar.gz
}

addtask unpack_extra after do_unpack before do_patch

# The .so files in iproute2-tc are modules, not traditional libraries
INSANE_SKIP_${PN}-tc = "dev-so"
