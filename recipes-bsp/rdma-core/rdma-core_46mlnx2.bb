SUMMARY = "Userspace support for InfiniBand/RDMA verbs"
DESCRIPTION = "This is the userspace components for the Linux Kernel's drivers Infiniband/RDMA subsystem."
SECTION = "libs"

LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING.BSD_MIT;md5=4cef3f976a07850eb6d2e5d1ef945e6b"

DEPENDS = "cmake libnl mlnx-ofa-kernel"
RDEPENDS_${PN} = "bash perl"

# The RPM used for this recipe requires xz tools to unpack and
# the poky classes don't detect that dependency automatically.
do_unpack[depends] += "xz-native:do_populate_sysroot"
do_cve_check[depends] += "${PN}:do_prepare_recipe_sysroot"

inherit cmake pkgconfig

SRC_URI = "file://rdma-core-46mlnx2-1.46358.src.rpm"
SRC_URI[md5sum] = "02573c2164638081e7966e7cd39db1be"
SRC_URI[sha256sum] = "2542a0b5a6df873cd967dd8ada22b78e0b95762ef6f389d7d0ab070c7bb9e3f0"

S = "${WORKDIR}/rdma-core-${PV}"

FILESEXTRAPATHS_prepend := "${OFED_SRC_PATH}/SRPMS:${TOPDIR}/mellanox/SRPMS:${MLNX_OFED_PATH}/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS:"

EXTRA_OECMAKE = " -DWITH_OFED_KERNEL=${TMPDIR}/work-shared/${MACHINE}/mlnx-ofa_kernel/default"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*"
FILES_${PN} += "/usr/lib/systemd/system/*"
INSANE_SKIP_${PN} += "dev-so"

OECMAKE_FIND_ROOT_PATH_MODE_PROGRAM = "BOTH"

do_unpack_extra() {
	cd ${WORKDIR}
	tar xzf rdma-core-${PV}.tgz
}

addtask unpack_extra after do_unpack before do_patch
