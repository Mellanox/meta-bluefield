SUMMARY = "The kernel part of MLNX_OFED"
SECTION = "mlnx-ofa-kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

COMPATIBLE_HOST = "aarch64.*-linux"
DIRFILES = "1"

inherit module
inherit systemd

SRC_URI = "file://mlnx-ofa_kernel-4.6-OFED.4.6.3.5.8.1.gaf1d902.src.rpm"
SRC_URI[md5sum] = "48559137138a0b39aef192e2078ffddb"

SRC_URI += "file://makefile.patch;patchdir=${S}/ofed_scripts;striplevel=0 \
            file://build-linux.m4.patch;patchdir=${S}/compat/config;striplevel=0 \
            file://parallel-build.m4.patch;patchdir=${S}/compat/config;striplevel=0 \
            file://nvme.conf \
            file://mlnx_blacklist.conf"

DEPENDS += "coreutils-native"
RDEPENDS_${PN} += "python"

do_cve_check[depends] += "${PN}:do_prepare_recipe_sysroot"

FILESEXTRAPATHS_prepend := "${OFED_SRC_PATH}/SRPMS:${TOPDIR}/mellanox/SRPMS:${MLNX_OFED_PATH}/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS:${TOPDIR}/mellanox/SRPMS:${TOPDIR}/distro/mlnx_ofed/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS:"

S = "${WORKDIR}/mlnx-ofa_kernel-${PV}"
MLNX_OFED_KERNEL_SHARED_DIR = "${TMPDIR}/work-shared/${MACHINE}"

PACKAGES = "${PN} ${PN}-dev ${PN}-blacklist ${PN}-openibd"

SYSTEMD_SERVICE_${PN}-openibd = "openibd.service"

SYSTEMD_PACKAGES = "${PN}-openibd"

SYSTEMD_AUTO_ENABLE = "enable"

REQUIRED_DISTRO_FEATURES = "systemd"

FILES_${PN}-blacklist += " \
        etc/modprobe.d/mlnx_blacklist.conf \
        "

FILES_${PN}-openibd += " \
	etc/init.d/openibd \
	etc/infiniband/openib.conf \
        "

FILES_${PN} += " \
	etc/udev/rules.d/90-ib.rules \
	etc/modprobe.d/mlnx.conf \
	etc/modprobe.d/nvme.conf \
	etc/modprobe.d/mlnx-eswitch.conf \
	etc/infiniband/mlx5.conf \
	etc/infiniband/vf-net-link-name.sh \
	etc/udev/rules.d/82-net-setup-link.rules \
	usr/*bin \
	usr/lib* \
	sbin/* \
	"

FILES_${PN}-dev += " \
	usr/src/* \
	"

python do_unpack_append() {
    import tarfile
    info = {}

    info['workdir'] = d.getVar('WORKDIR')
    info['pv'] = d.getVar('PV')
    info['tarfile'] = 'mlnx-ofa_kernel-' + info['pv'] + '.tgz'
    info['tarpath'] = os.path.join(info['workdir'], info['tarfile'])

    with tarfile.open(info['tarpath'], "r:gz") as tar:
        tar.extractall(info['workdir'])
}

do_configure() {
		cd ${S}; \
		ln -snf ofed_scripts/configure; \
		ln -snf ofed_scripts/makefile; \
		ln -snf ofed_scripts/Makefile; \
		LDFLAGS="" \
		${MAKE} clean || :
}

do_compile() {
		set -x; \
		cd ${S}; \
		ln -snf ofed_scripts/configure; \
		ln -snf ofed_scripts/makefile; \
		ln -snf ofed_scripts/Makefile; \
		mkdir -p ${S}-src; \
		mkdir -p ${S}-src/mlnx-ofa_kernel/default; \
		cp -r ${S} ${S}-src/mlnx-ofa_kernel-${PV}; \
		env host_alias=${HOST_SYS} build_alias=${BUILD_SYS} ./configure --force-autogen --with-linux ${KBUILD_OUTPUT}/source --with-linux-obj ${KBUILD_OUTPUT} --kernel-version ${KERNEL_VERSION} --with-core-mod --with-user_mad-mod --with-user_access-mod --with-addr_trans-mod --with-mlx5-mod --with-bf-device-emulation --with-bf-power-failure-event --with-mlxfw-mod --with-ipoib-mod --with-nvmf_host-mod --with-nvmf_target-mod --with-iser-mod --with-isert-mod --without-git; \
		cp -r ${S}/include ${S}-src/mlnx-ofa_kernel/default; \
		cp -r ${S}/compat* ${S}-src/mlnx-ofa_kernel/default; \
		cp -r ${S}/config* ${S}-src/mlnx-ofa_kernel/default; \
		cp -r ${S}/ofed_scripts ${S}-src/mlnx-ofa_kernel/default; \
                env ARCH=${ARCH} CC=${CROSS_COMPILE}gcc LD=${CROSS_COMPILE}ld \
                CROSS_COMPILE="${CROSS_COMPILE}" LDFLAGS= \
                ${MAKE} kernel
}

do_install() {
		set -x; \
		cd ${S}; \
             env ARCH=${ARCH} CC=${CROSS_COMPILE}gcc LD=${CROSS_COMPILE}ld CROSS_COMPILE="${CROSS_COMPILE}" \
                LDFLAGS= \
		INSTALL_MOD_PATH=${D} KERNELRELEASE=${KERNEL_VERSION} \
		INSTALL_MOD_DIR=updates \
		${MAKE} install_kernel

		mkdir -p ${D}/usr/src
		cp -r ${S}-src/* ${D}/usr/src/
		mkdir -p ${MLNX_OFED_KERNEL_SHARED_DIR}
		cp -a ${S}-src/* ${MLNX_OFED_KERNEL_SHARED_DIR}/
		rm -rf ${S}-src
		INSTALL_MOD_PATH=${D} KERNELRELEASE=${KERNEL_VERSION} \
		INSTALL_MOD_DIR=updates \
		${MAKE} install_scripts
		mkdir -p ${D}/lib/systemd/system
		cp ofed_scripts/openibd.service ${D}/lib/systemd/system/openibd.service

		sed -i -e "s/^FORCE=0/FORCE=1/" ${D}/etc/init.d/openibd

		install -d ${D}${sysconfdir}/modprobe.d
		install -m 0644 ${WORKDIR}/nvme.conf ${D}${sysconfdir}/modprobe.d/nvme.conf
		install -m 0644 ${WORKDIR}/mlnx_blacklist.conf ${D}${sysconfdir}/modprobe.d/mlnx_blacklist.conf
}
