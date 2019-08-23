EXTRA_OEMAKE = "CC='${CC}' KERNEL_INCLUDE=${STAGING_INCDIR} \
 DOCDIR=${docdir}/iproute2 SUBDIRS='lib tc ip bridge misc genl \
 devlink' SBINDIR='${base_sbindir}' LIBDIR='${libdir}'"

DEPENDS += "libmnl"
