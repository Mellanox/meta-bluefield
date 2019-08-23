SUMMARY = "An image with most of the non-graphical libraries and \
development tools such as GCC, gdb, Linux kernel source and much more."

IMAGE_FEATURES += "tools-debug tools-profile tools-testapps tools-sdk debug-tweaks"

require recipes-extended/images/core-image-lsb-dev.bb

include core-image-full.inc

inherit module-base

IMAGE_INSTALL_append = " git libcap libpcap libpcap-dev tcpdump lldpad libxext \
sntp libstdc++-dev tk python-dev curl-dev tcl-dev openssl-dev atk cairo tcsh \
libmnl libmnl-dev iptables-dev traceroute python-setuptools python-dev \
elfutils-dev net-tools systemd-dev libsysfs systemtap rpm-build libhugetlbfs \
db-dev ninja glib-2.0-dev gtk+ numactl-dev libnl-dev createrepo-c db-dev \
initscripts openipmi-dev cunit-dev valgrind lcov"

# IMAGE_INSTALL_append = " openvswitch strongswan strongswan-systemd"
# KERNEL_MODULE_AUTOLOAD += "openvswitch"

IMAGE_INSTALL_append = " kernel-devsrc"
IMAGE_INSTALL_append = " mlnx-ofa-kernel-dev"

IMAGE_INSTALL_append = " tigervnc packagegroup-core-x11 xterm \
                         xclock twm xfontsel xsetroot xlsfonts \
                         xrefresh xmessage xsetmode font-misc-misc"

fakeroot do_populate_update_src () {
	# Create a symlink, needed for out-of-tree kernel modules build
	rm -rf  ${IMAGE_ROOTFS}/lib/modules/${KERNEL_VERSION}/build
	lnr ${IMAGE_ROOTFS}${KERNEL_SRC_PATH} ${IMAGE_ROOTFS}/lib/modules/${KERNEL_VERSION}/build
        # We disable connman here because it conflicts with systemd-networkd
        # and udev network interface naming.
        lnr ${IMAGE_ROOTFS}/dev/null ${IMAGE_ROOTFS}/etc/systemd/system/connman.service

        if [ ! -z "${MLNX_BLUEFIELD_EXTRA_DEV_PATH}" ]; then
          if [ -d ${MLNX_BLUEFIELD_EXTRA_DEV_PATH} ]; then
             install -d ${IMAGE_ROOTFS}/opt/mellanox/extra
             cp ${MLNX_BLUEFIELD_EXTRA_DEV_PATH}/* ${IMAGE_ROOTFS}/opt/mellanox/extra
          fi
        fi
}

IMAGE_PREPROCESS_COMMAND += "do_populate_update_src; "
