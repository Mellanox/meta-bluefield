SUMMARY = "OpenvSwitch"
DESCRIPTION = "\
	Open vSwitch is a production quality, multilayer virtual switch \
	licensed under the open source Apache 2.0 license. It is designed \
	to enable massive network automation through programmatic extension, \
	while still supporting standard management interfaces and protocols \
	(e.g. NetFlow, sFlow, SPAN, RSPAN, CLI, LACP, 802.1ag) \
	"

HOMEPAGE = "http://openvswitch.org/"
SECTION = "networking"
LICENSE = "Apache-2"

DEPENDS += "bridge-utils openssl python3 perl python3-six-native coreutils-native"

RDEPENDS_${PN} += "util-linux-uuidgen util-linux-libuuid coreutils \
        python3 perl perl-module-strict ${PN}-switch \
        bash python3-twisted python3-six"
RDEPENDS_${PN}-switch = "${PN} openssl procps util-linux-uuidgen"
RDEPENDS_${PN}-pki = "${PN}"
RDEPENDS_${PN}-brcompat = "${PN} ${PN}-switch"

# Some installers will fail because of an install order based on
# rdeps.  E.g. ovs-pki calls sed in the postinstall.  sed may be
# queued for install later.
RDEPENDS_${PN} += "sed gawk grep"

SRC_URI = "\
	file://openvswitch-switch \
	file://openvswitch-switch-setup \
	"

EXTRA_OECONF += "\
	PYTHON=python3 \
	PYTHON3=python3 \
	PERL=${bindir}/perl \
	"
CONFIGUREOPT_DEPTRACK = ""

# Don't compile kernel modules by default since it heavily depends on
# kernel version. Use the in-kernel module for now.
# distro layers can enable with EXTRA_OECONF_pn_openvswitch += ""
# EXTRA_OECONF = "--with-linux=${STAGING_KERNEL_DIR} KARCH=${TARGET_ARCH}"

ALLOW_EMPTY_${PN}-pki = "1"
PACKAGES =+ "${PN}-switch ${PN}-brcompat ${PN}-pki"

FILES_${PN}-brcompat = "${sbindir}/ovs-brcompatd"

FILES_${PN}-switch = "\
	${sysconfdir}/init.d/openvswitch-switch \
	${sysconfdir}/default/openvswitch-switch \
	${systemd_unitdir}/system/ovs-vswitchd.service \
	${systemd_unitdir}/system/openvswitch.service \
	${systemd_unitdir}/system/ovsdb-server.service \
	"

# silence a warning
FILES_${PN} += "${datadir}/ovsdbmonitor"
FILES_${PN} += "/run"

FILES_${PN} += "${libdir}/python${PYTHON_BASEVERSION}/"
inherit autotools update-rc.d systemd python3native

SYSTEMD_PACKAGES = "${PN}-switch"
SYSTEMD_SERVICE_${PN}-switch = " \
    ovsdb-server.service \
    ovs-vswitchd.service \
    openvswitch.service \
"

INITSCRIPT_PACKAGES = "${PN}-switch"
INITSCRIPT_NAME_${PN}-switch = "openvswitch-switch"
INITSCRIPT_PARAMS_${PN}-switch = "defaults 71"

do_configure_prepend() {
	# Work around the for Makefile CC=$(if ....) by swapping out any
	# "-Wa," assembly directives with "-Xassembler
	CC=`echo '${CC}' | sed 's/-Wa,/-Xassembler /g'`
}

do_install_append() {
	install -d ${D}/${sysconfdir}/default/
	install -m 660 ${WORKDIR}/openvswitch-switch-setup ${D}/${sysconfdir}/default/openvswitch-switch
	install -d ${D}/${sysconfdir}/init.d/
	install -m 755 ${WORKDIR}/openvswitch-switch ${D}/${sysconfdir}/init.d/openvswitch-switch
	true || rm -fr ${D}/${datadir}/${PN}/pki

	install -d ${D}/${systemd_unitdir}/system/

	install -m 644 ${WORKDIR}/ovs-vswitchd.service  \
		${D}/${systemd_unitdir}/system/ovs-vswitchd.service

	install -m 644 ${WORKDIR}/ovsdb-server.service  \
		${D}/${systemd_unitdir}/system/ovsdb-server.service

        install -m 644 ${S}/rhel/etc_openvswitch_default.conf \
                ${D}/${sysconfdir}/openvswitch/default.conf

	install -m 644 ${S}/rhel/usr_lib_systemd_system_openvswitch.service \
		${D}/${systemd_unitdir}/system/openvswitch.service

	oe_runmake modules_install INSTALL_MOD_PATH=${D}
	install -d ${D}${libdir}/python${PYTHON_BASEVERSION}/site-packages
}

pkg_postinst_${PN}-pki () {
	# can't do this offline
	if [ "x$D" != "x" ]; then
		exit 1
	fi
	if test ! -d $D/${datadir}/${PN}/pki; then
		ovs-pki init --dir=$D/${datadir}/${PN}/pki
	fi
}
