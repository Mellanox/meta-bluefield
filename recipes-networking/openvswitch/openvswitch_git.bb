require openvswitch.inc

DEPENDS += "virtual/kernel"

S = "${WORKDIR}/git"
PV = "2.11.0+${SRCREV}"

FILESEXTRAPATHS_append := "${THISDIR}/${PN}-git:"

SRCREV = "9ebe7950350fcd3f83e61dbf42fcfddcb5213fae"
SRC_URI = "file://openvswitch-switch \
           file://openvswitch-switch-setup \
           file://Makefile.am.patch;striplevel=0 \
           file://ovsdb-server.service \
           file://ovs-vswitchd.service \
           git://github.com/openvswitch/ovs.git;protocol=git;branch=branch-2.11 \
           "

LIC_FILES_CHKSUM = "file://LICENSE;md5=1ce5d23a6429dff345518758f13aaeab"

DPDK_INSTALL_DIR ?= "/opt/dpdk"

PACKAGECONFIG ?= "libcap-ng"
PACKAGECONFIG[dpdk] = "--with-dpdk=${STAGING_DIR_TARGET}${DPDK_INSTALL_DIR}/share/${TARGET_ARCH}-native-linuxapp-gcc,,dpdk,dpdk"
PACKAGECONFIG[libcap-ng] = "--enable-libcapng,--disable-libcapng,libcap-ng,"

# Don't compile kernel modules by default since it heavily depends on
# kernel version. Use the in-kernel module for now.
# distro layers can enable with EXTRA_OECONF_pn_openvswitch += ""
# EXTRA_OECONF += "--with-linux=${STAGING_KERNEL_BUILDDIR} --with-linux-source=${STAGING_KERNEL_DIR} KARCH=${TARGET_ARCH}"

# silence a warning
FILES_${PN} += "/lib/modules"

do_install_append() {
	oe_runmake modules_install INSTALL_MOD_PATH=${D}
	rm -rf ${D}/${localstatedir}/run
}
