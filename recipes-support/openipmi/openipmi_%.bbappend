# Don't use swig.  There appears to be an issue building openipmigui
# and enabling/building openipmi modules for python.
# Intentionally not using swig forces the build to bypass this step.
# What this means is that until we fix this, you cannot load the
# openipmi python modules and write python scripts that use IPMI.
EXTRA_OECONF = "\
    --with-swig=no \
    --with-mellanox-bf \
    "
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
SRC_URI_append = " file://ipmi.sysconf.patch"
SRC_URI_append = " file://smbus-channel.patch"

SYSTEMD_SERVICE_${PN} =+ "mlx_ipmid.service"

FILES_${PN} += "${systemd_system_unitdir}/mlx_ipmid.service"

# We want systemd to start openipmi and load the drivers at boot time.
SYSTEMD_AUTO_ENABLE = "enable"
